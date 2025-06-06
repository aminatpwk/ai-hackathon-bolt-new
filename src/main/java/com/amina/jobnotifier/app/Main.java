package com.amina.jobnotifier.app;

import com.amina.jobnotifier.controller.EmailController;

public class Main {
    public static void main(String[] args) throws Exception {
        EmailController emailController = new EmailController();
        emailController.checkInbox();
    }
}