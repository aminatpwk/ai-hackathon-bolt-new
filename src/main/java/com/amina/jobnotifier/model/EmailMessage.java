package com.amina.jobnotifier.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * EmailMessage POJO class
 */
public class EmailMessage {
    private String from;
    private String subject;
    private String content;
    private LocalDateTime receivedDate;
    private boolean read;
    private RejectionCategory category;

    public enum RejectionCategory{
        REJECTION("Rejection"),
        POTENTIAL_REJECTION("Potential Rejection"),
        OTHER("Other"),
        UNPROCESSED("Unprocessed");
        private String displayName;
        RejectionCategory(String displayName){
            this.displayName = displayName;
        }
        public String getDisplayName(){
            return displayName;
        }
    }

    public EmailMessage(){
        this.read = false;
        this.category = RejectionCategory.UNPROCESSED;
    }

    public EmailMessage(String from, String subject, String content){
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.category = RejectionCategory.UNPROCESSED;
    }

    @Override
    public String toString() {
        return "EmailMessage [from=" + from + ", subject=" + subject + ", content=" + content + "]";
    }

    public String getFrom() {
        return from;
    }
    public String getSubject() {
        return subject;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }
    public boolean isRead() {
        return read;
    }
    public RejectionCategory getCategory() {
        return category;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setContent(String content) {
        this.content=content;
    }
    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
    public void setCategory(RejectionCategory category) {
        this.category = category;
    }
}
