package com.amina.jobnotifier.controller;

import com.amina.jobnotifier.model.EmailAccount;
import com.amina.jobnotifier.service.EmailService;
import io.github.cdimascio.dotenv.Dotenv;

public class EmailController {
    private final EmailService emailService;

    public EmailController() {
        this.emailService = new EmailService();
    }

    public void checkInbox() {
        Dotenv dotenv = Dotenv.load();

        String email = dotenv.get("EMAIL_ADDRESS");
        String password = dotenv.get("EMAIL_APP_PASSWORD");

        EmailAccount account = EmailAccount.getCommonSettings("gmail", email, password);

        emailService.checkAccount(account);
    }
}
