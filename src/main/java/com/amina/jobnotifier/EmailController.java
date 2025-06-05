package com.amina.jobnotifier;

import com.amina.jobnotifier.service.EmailService;


public class EmailController {
    private final EmailService emailService;

    public EmailController() {
        this.emailService = new EmailService();
    }

    public void checkInbox() throws Exception{
        emailService.fetchAndPrintRecentEmails();
    }
}
