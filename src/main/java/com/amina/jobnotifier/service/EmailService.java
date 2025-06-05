package com.amina.jobnotifier.service;

import com.amina.jobnotifier.model.EmailMessage;
import com.amina.jobnotifier.util.EmailUtils;
import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class interacts with gmal over IMAP to read messages from the inbox and to extract subject, sender and body
 * of emails.
 * Also converts raw emails to a clean format like in the EmailMessage.
 */
public class EmailService {
    public void fetchAndPrintRecentEmails() throws Exception{
        Dotenv dotenv = Dotenv.load();
        String app_email = dotenv.get("EMAIL_ADDRESS");
        String password = dotenv.get("EMAIL_APP_PASSWORD");
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.imaps.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props, null);
        Store store = session.getStore();
        store.connect("imap.gmail.com", 993, app_email, password);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();

        List<EmailMessage> emailMessages = new ArrayList<>();
        for(int i = Math.max(0, messages.length-5); i <messages.length; i++){
            Message msg = messages[i];
            String subject = msg.getSubject();
            String from = msg.getFrom()[0].toString();
            String content = EmailUtils.getTextFromMessage(msg);
            emailMessages.add(new EmailMessage(from, subject, content));
        }

        for(EmailMessage email : emailMessages){
            System.out.println(email);
        }

        inbox.close(false);
        store.close();

    }
}
