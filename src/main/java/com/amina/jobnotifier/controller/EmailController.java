package com.amina.jobnotifier.controller;

import com.amina.jobnotifier.model.EmailAccount;
import com.amina.jobnotifier.service.EmailService;
import com.amina.jobnotifier.service.RejectionService;
import io.github.cdimascio.dotenv.Dotenv;

public class EmailController {
    private final EmailService emailService;
    private final RejectionService rejectionService;

    public EmailController() {
        this.emailService = new EmailService();
        this.rejectionService = new RejectionService();
    }

    public void checkInbox() {
        Dotenv dotenv = Dotenv.load();

        String email = dotenv.get("EMAIL_ADDRESS");
        String password = dotenv.get("EMAIL_APP_PASSWORD");

        EmailAccount account = EmailAccount.getCommonSettings("gmail", email, password);

        emailService.checkAccount(account);
    }
}
