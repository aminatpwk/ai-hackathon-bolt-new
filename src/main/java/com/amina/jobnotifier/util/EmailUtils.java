package com.amina.jobnotifier.util;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

/**
 * This class is responsible as a utility for parsing email content.
 */
public class EmailUtils {

    /**
     * Takes a message object (from JavaMail API) and returns the plain text content as string.
     * @param message
     * @return
     * @throws IOException
     * @throws MessagingException
     */
    public static String getTextFromMessage(Message message) throws IOException, MessagingException {
            if(message.isMimeType("text/plain")) {
                return message.getContent().toString();
            }else if(message.isMimeType("multipart/*")) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                return getTextFromMimeMultipart(mimeMultipart);
            }
            return "";
    }

    /**
     * Helper method to process multipart messages in order to find and extract plain text content.
     * @param mimeMultipart
     * @return
     * @throws IOException
     * @throws MessagingException
     */
    public static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
                break;
            }
        }
        return result.toString();
    }
}
