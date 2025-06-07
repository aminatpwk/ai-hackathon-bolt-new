package com.amina.jobnotifier.service;

import com.amina.jobnotifier.model.EmailAnalysisResult;
import com.amina.jobnotifier.model.EmailMessage;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RejectionService {
    private static Logger logger = LoggerFactory.getLogger(RejectionService.class);
    private List<Pattern> rejectionPatterns;
    private List<String> rejectionKeywords;
    private List<String> hrDomains;

    private OpenAIService openAIService;
    private boolean useOpenAI;

    public RejectionService(){
        String aiKey = Dotenv.configure().load().get("OPENAI_API_KEY");
        if(aiKey != null && !aiKey.trim().isEmpty()){
            this.openAIService = new OpenAIService(aiKey);
            this.useOpenAI = true;
            logger.info("OpenAI service created");
        }else{
            this.openAIService = null;
            this.useOpenAI = false;
            logger.warn("OpenAI API key not configured, switching to default pattern");
        }

        initializePatterns();
    }

    private void initializePatterns() {
        rejectionPatterns = new ArrayList<>();
        rejectionPatterns.add(Pattern.compile("(?i)we (regret|are sorry) to inform you"));
        rejectionPatterns.add(Pattern.compile("(?i)thank you for (your interest|applying).*?unfortunately"));
        rejectionPatterns.add(Pattern.compile("(?i)we (have decided|will not) (to )?proceed with"));
        rejectionPatterns.add(Pattern.compile("(?i)we (will not be|are not) (moving forward|proceeding)"));
        rejectionPatterns.add(Pattern.compile("(?i)we are unable to offer you"));
        rejectionPatterns.add(Pattern.compile("(?i)after careful consideration.*?not selected"));
        rejectionPatterns.add(Pattern.compile("(?i)not right fit"));
        rejectionPatterns.add(Pattern.compile("(?i)position has been filled"));

        rejectionKeywords = new ArrayList<>();
        rejectionKeywords.add("unfortunately");
        rejectionKeywords.add("regret");
        rejectionKeywords.add("sorry");
        rejectionKeywords.add("not selected");
        rejectionKeywords.add("not successful");
        rejectionKeywords.add("other candidates");
        rejectionKeywords.add("wish you luck");
        rejectionKeywords.add("future opportunities");
        rejectionKeywords.add("keep your resume");
        rejectionKeywords.add("application status");

        hrDomains = new ArrayList<>();
        hrDomains.add("recruiter");
        hrDomains.add("recruiting");
        hrDomains.add("talent");
        hrDomains.add("hr");
        hrDomains.add("jobs");
        hrDomains.add("careers");
        hrDomains.add("indeed");
        hrDomains.add("linkedin");
    }

    public EmailMessage detectRejection(EmailMessage email){
        if(useOpenAI){
            return analyzeWithOpenAI(email);
        }else{
            return analyzeWithPatterns(email);
        }
    }

    private EmailMessage analyzeWithOpenAI(EmailMessage email){
        try{
            EmailAnalysisResult result = openAIService.analyzeEmail(
                    email.getSubject(),
                    email.getContent(),
                    email.getFrom()
            );
            if (result.isRejection()) {
                email.setCategory(EmailMessage.RejectionCategory.REJECTION);
            }
            logger.debug("OpenAI analysis - Subject: '{}', Category: {}, Company:{}", email.getSubject(), email.getCategory(), email.getFrom());
        }catch(Exception e){
            logger.error("Error in OpenAI analysis, switching to default pattern");
            return analyzeWithPatterns(email);
        }
        return email;
    }

    private EmailMessage analyzeWithPatterns(EmailMessage email){
        String content = email.getContent() != null ? email.getContent() : "";
        String subject = email.getSubject() != null ? email.getSubject() : "";
        String sender = email.getFrom() != null ? email.getFrom() : "";
        String fullText = subject+" "+content;

        double patternScore = checkRejectionPatterns(fullText);
        double keywordScore = checkRejectionKeywords(fullText);
        double senderScore = checkSender(sender);
        double probability = (patternScore * 0.6) + (keywordScore * 0.25) + (senderScore * 0.15);
        if(probability >= 0.75){
            email.setCategory(EmailMessage.RejectionCategory.REJECTION);
        }else{
            email.setCategory(EmailMessage.RejectionCategory.OTHER);
        }

        logger.debug("Email analyzed - Subject: '{}', Category: {}, Probability: {}", subject, email.getCategory(), probability);

        return email;
    }
    private double checkRejectionPatterns(String content){
        int matchCount = 0;
        for(Pattern pattern : rejectionPatterns){
            if(pattern.matcher(content).find()){
                matchCount++;
            }
        }

        return Math.min(1.0, matchCount/(double)rejectionPatterns.size()*1.5);
    }

    private double checkRejectionKeywords(String content){
        String lowerContent = content.toLowerCase();
        int matchCount = 0;
        for(String keyword : rejectionKeywords){
            if(lowerContent.contains(keyword.toLowerCase())){
                matchCount++;
            }
        }

        return Math.min(1.0, matchCount/(double)rejectionKeywords.size()*1.5);
    }

    private double checkSender(String sender){
        if(sender == null){
            return 0.0;
        }
        String lowerSender = sender.toLowerCase();
        for(String domain : hrDomains){
            if(lowerSender.contains(domain.toLowerCase())){
                return 0.8;
            }
        }
        if (lowerSender.contains("recruiter") ||
                lowerSender.contains("talent") ||
                lowerSender.contains("hr") ||
                lowerSender.contains("human resources") ||
                lowerSender.contains("recruiting") ||
                lowerSender.contains("hiring")) {
            return 0.7;
        }

        return 0.1;
    }

    public void cleanup(){
        if(openAIService != null){
            openAIService.close();
        }
    }
}
