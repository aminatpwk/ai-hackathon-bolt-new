package com.amina.jobnotifier.util;

import java.awt.*;

import com.amina.jobnotifier.model.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class NotificationUtil {
    private static Logger logger = LoggerFactory.getLogger(NotificationUtil.class);
    private static NotificationUtil instance;
    private boolean systemTrayAvailable;
    private TrayIcon trayIcon;

    public NotificationUtil() {
        initSystemTray();
    }

    public static synchronized NotificationUtil getInstance(){
        if(instance == null){
            instance = new NotificationUtil();
        }
        return instance;
    }

    public void initSystemTray() {
        if(!SystemTray.isSupported()){
            systemTrayAvailable = false;
            logger.warn("System tray is not supported");
            return;
        }

        try{
            SystemTray tray = SystemTray.getSystemTray();
            Image image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("images/tray_icon.png")));
            trayIcon = new TrayIcon(image, "Email Notifier AI");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Listening for job rejections...");
            tray.add(trayIcon);
            systemTrayAvailable = true;
            logger.info("System tray is initialized");
        }catch(Exception e){
            systemTrayAvailable = false;
            logger.error("Error initializing system tray: {}", e.getMessage(), e);
        }
    }

    public void showRejectionNotification(EmailMessage email){
        if(!systemTrayAvailable || trayIcon == null){
            logger.warn("Cannot show notification, system tray not available.");
            return;
        }
        for(TrayIcon icon : SystemTray.getSystemTray().getTrayIcons()){
            icon.displayMessage("New Email", String.valueOf(email), TrayIcon.MessageType.INFO);
        }
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
