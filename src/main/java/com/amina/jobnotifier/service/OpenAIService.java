package com.amina.jobnotifier.service;

import com.amina.jobnotifier.model.EmailAnalysisResult;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;

public class OpenAIService {
    private Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final String MODEL = "gpt-3.5-turbo";
    private final String apiKey;
    private final CloseableHttpClient httpClient;

    public OpenAIService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClients.createDefault();
    }

    public EmailAnalysisResult analyzeEmail(String subject, String content, String from){
        try{
            String prompt = createAnalysisPrompt(subject, content, from);
            String response = callOpenAI(prompt);
            return parseAnalysisResponse(response);
        }catch(Exception e){
            logger.error("Error analyzing email with OpenAI: {}", e.getMessage(), e);
            return createFallbackResult();
        }
    }

    private String createAnalysisPrompt(String subject, String content, String from){
        return String.format(""" 
         Analyze the following email and determine if it's related to a job application rejection or negative response.
         Email Subject: %s
         Sender: %s
         Content: %s
         Please respond with a JSON object containing: 
         {
             "isJobRelated": boolean,
             "isRejection": boolean,
             "companyName": "string or null",
         }
         
         Guidelines:
          - isJobRelated: true if the email is about job applications, interviews or hiring
          - isRejection: true if it's a clear rejection/decline
          - Extract company name and position title if mentioned
         """, subject, from, content);
    }

    private String callOpenAI(String prompt) throws IOException, ParseException {
        HttpPost request = new HttpPost(OPENAI_API_URL);
        request.setHeader("Authorization", "Bearer " +apiKey);
        request.setHeader("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);

        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        messages.put(message);
        requestBody.put("messages", messages);

        StringEntity entity = new StringEntity(requestBody.toString(), ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            if(response.getCode() != 200){
                logger.error("OpenAI API error: {} - {}", response.getCode(), responseBody);
                throw new IOException("OpenAI API request failed: "+response.getCode());
            }
            JSONObject responseJson = new JSONObject(responseBody);
            JSONArray choices = responseJson.getJSONArray("choices");
            if(choices.length() > 0){
                JSONObject choice = choices.getJSONObject(0);
                JSONObject messageObj = choice.getJSONObject("message");
                return messageObj.getString("content");
            }
            throw new IOException("No response content from OpenAI");
        } catch (org.apache.hc.core5.http.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private EmailAnalysisResult parseAnalysisResponse(String response){
        try{
            String jsonStr = response.trim();
            if(jsonStr.startsWith("```json")){
                jsonStr = jsonStr.substring(7);
            }
            if(jsonStr.endsWith("```")){
                jsonStr = jsonStr.substring(0,jsonStr.length()-3);
            }

            JSONObject json = new JSONObject(jsonStr.trim());
            EmailAnalysisResult result = new EmailAnalysisResult();
            result.setJobRelated(json.optBoolean("isJobRelated", false));
            result.setRejection(json.optBoolean("isRejection", false));
            result.setCompanyName(json.optString("companyName", null));
            logger.info("API call finished, got response");
            return result;
        }catch(Exception e){
            logger.error("Error parsing OPENAI response: {}", e.getMessage(), e);
            return createFallbackResult();
        }
    }

    private EmailAnalysisResult createFallbackResult(){
        EmailAnalysisResult result = new EmailAnalysisResult();
        result.setJobRelated(false);
        result.setRejection(false);
        result.setCompanyName(null);
        return result;
    }

    public void close(){
        try{
            httpClient.close();
        }catch(IOException e){
            logger.error("Error closing HTTP client: {}", e.getMessage(), e);
        }
    }
}
