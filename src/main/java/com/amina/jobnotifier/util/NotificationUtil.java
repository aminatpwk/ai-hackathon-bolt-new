package com.amina.jobnotifier.util;

import java.awt.*;

import com.amina.jobnotifier.model.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.image.BufferedImage;

public class NotificationUtil {
    private static Logger logger = LoggerFactory.getLogger(NotificationUtil.class);
    private static NotificationUtil instance;
    private boolean systemTrayAvailable;
    private TrayIcon trayIcon;

    private NotificationUtil() {
        initSystemTray();
    }

    public static synchronized NotificationUtil getInstance(){
        if(instance == null){
            instance = new NotificationUtil();
        }
        return instance;
    }

    private void initSystemTray() {
        if(!SystemTray.isSupported()){
            systemTrayAvailable = false;
            logger.warn("System tray is not supported");
            return;
        }

        try{
            SystemTray tray = SystemTray.getSystemTray();
            int trayIconSize = tray.getTrayIconSize().height;
            Image image = createTrayIconImage(trayIconSize);
            trayIcon = new TrayIcon(image, "Email Notifier AI");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
            systemTrayAvailable = true;
            logger.info("System tray is initialized");
        }catch(AWTException e){
            systemTrayAvailable = false;
            logger.error("Error initializing system tray: {}", e.getMessage(), e);
        }
    }

    private Image createTrayIconImage(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(16, 63, 101));
        g.fillRect(0, 0, size, size);
        g.setColor(Color.WHITE);

        //ikona e app si @
        g.drawString("@", size/3, (int)(size*0.7));
        g.dispose();
        return image;
    }

    public void showRejectionNotification(EmailMessage email){
        if(!systemTrayAvailable || trayIcon == null){
            logger.warn("Cannot show notification, system tray not available.");
            return;
        }
        String title = "Job Application Rejection";
        String message = "Rejection from: "+email.getFrom()+"\n"+"Subject: "+email.getSubject();
        trayIcon.displayMessage(title, message, MessageType.INFO);
        logger.info("Displayed rejection notification: {}", email.getSubject());
    }

    //ne raste shutdowni
    public void cleanup(){
        if(systemTrayAvailable && trayIcon != null){
            SystemTray.getSystemTray().remove(trayIcon);
            logger.info("Removed system tray");
        }
    }
}
