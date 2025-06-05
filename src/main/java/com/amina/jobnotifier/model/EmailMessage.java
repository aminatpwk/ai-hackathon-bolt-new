package com.amina.jobnotifier.model;

/**
 * EmailMessage POJO class
 */
public class EmailMessage {
    private final String from;
    private final String subject;
    private final String content;

    public EmailMessage(String from, String subject, String content){
        this.from = from;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public String toString() {
        return "EmailMessage [from=" + from + ", subject=" + subject + ", content=" + content + "]";
    }
}
