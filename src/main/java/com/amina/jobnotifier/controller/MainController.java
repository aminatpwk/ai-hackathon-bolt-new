package com.amina.jobnotifier.controller;

import com.amina.jobnotifier.service.RejectionService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MainController {
    @FXML
    private TextArea logArea;
    private EmailController emailController = new EmailController();
    @FXML
    private void onStartClicked(){
        log("Starting to check inboxes...");
        new Thread(() -> {
            try{
                emailController.checkInbox();
                log("Inbox check complete.");
            }catch(Exception e){
                log("Error: "+e.getMessage());
            }
        }).start();
    }

    private void log(String message){
        Platform.runLater(() -> logArea.appendText(message+"\n"));
    }
}
