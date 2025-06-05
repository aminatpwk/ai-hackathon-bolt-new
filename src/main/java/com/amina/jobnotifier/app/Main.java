package com.amina.jobnotifier.app;

import com.amina.jobnotifier.EmailController;

public class Main {
    public static void main(String[] args) throws Exception {
        EmailController emailController = new EmailController();
        emailController.checkInbox();
    }
}