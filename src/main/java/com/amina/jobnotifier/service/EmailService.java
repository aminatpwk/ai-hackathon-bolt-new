package com.amina.jobnotifier.service;

import com.amina.jobnotifier.model.EmailAccount;
import com.amina.jobnotifier.model.EmailMessage;
import com.amina.jobnotifier.util.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void checkAccount(EmailAccount account){
        logger.info("Checking account: {}", account.getDisplayName());

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", account.getImapServer());
        props.put("mail.imaps.port", String.valueOf(account.getImapPort()));
        props.put("mail.imaps.ssl.enable", String.valueOf(account.isImapSsl()));
        props.put("mail.imaps.ssl.protocols", "TLSv1.2");

        try {
            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");
            store.connect(account.getImapServer(), account.getEmail(), account.getPassword());

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            logger.info("Found {} unread messages in {}", messages.length, account.getEmail());
            if (messages.length == 0) {
                logger.info("No unread emails found.");
            }

            processMessages(messages, account);

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            logger.error("Error connecting or fetching email: {}", e.getMessage(), e);
        }
    }

    private void processMessages(Message[] messages, EmailAccount account) {
        List<EmailMessage> emailMessages = new ArrayList<>();
        for (Message msg : messages) {
            try {
                String subject = msg.getSubject();
                String from = msg.getFrom()[0].toString();
                String content = EmailUtils.getTextFromMessage(msg);

                EmailMessage emailMessage = new EmailMessage(from, subject, content);
                emailMessages.add(emailMessage);

                logger.info("Unread Email:\nFrom: {}\nSubject: {}\nContent: {}", from, subject, content);

            } catch (Exception e) {
                logger.error("Failed to parse message: {}", e.getMessage(), e);
            }
        }
    }
}
