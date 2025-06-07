package com.amina.jobnotifier.app;

import com.amina.jobnotifier.util.NotificationUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private NotificationUtil notificationUtil = new NotificationUtil();

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Job Rejection Notifier");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        notificationUtil.initSystemTray();
    }

    public static void main(String[] args) {
        launch();
    }
}
