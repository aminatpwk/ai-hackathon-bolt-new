package com.amina.jobnotifier.model;

/**
 * This class represents an email account with different email support.
 */
public class EmailAccount {
    private String id;
    private String name;
    private String email;
    private String password;
    private String imapServer;
    private int imapPort;
    private boolean imapSsl;
    private String smtpServer;
    private int smtpPort;
    private boolean smtpSsl;
    private boolean active;

    public EmailAccount() {
        this.active = true;
        this.imapPort = 993;
        this.imapSsl = false;
        this.smtpPort = 587;
        this.smtpSsl = true;
    }

    public EmailAccount(String name, String email, String password, String imapServer, int imapPort, boolean imapSssl){
        this();
        this.name = name;
        this.email = email;
        this.password = password;
        this.imapServer = imapServer;
        this.imapPort = imapPort;
        this.imapSsl = imapSssl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImapServer() {
        return imapServer;
    }

    public void setImapServer(String imapServer) {
        this.imapServer = imapServer;
    }

    public int getImapPort() {
        return imapPort;
    }

    public void setImapPort(int imapPort) {
        this.imapPort = imapPort;
    }

    public boolean isImapSsl() {
        return imapSsl;
    }

    public void setImapSsl(boolean imapSsl) {
        this.imapSsl = imapSsl;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public boolean isSmtpSsl() {
        return smtpSsl;
    }

    public void setSmtpSsl(boolean smtpSsl) {
        this.smtpSsl = smtpSsl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDisplayName(){
        return name + " <"+email+">";
    }

    public static EmailAccount getCommonSettings(String provider, String email, String password){
        EmailAccount account = new EmailAccount();
        account.setEmail(email);
        account.setPassword(password);
        switch(provider.toLowerCase()){
            case "gmail":
                account.setName("Gmail");
                account.setImapServer("imap.gmail.com");
                account.setImapPort(993);
                account.setImapSsl(true);
                account.setSmtpServer("smtp.gmail.com");
                account.setSmtpPort(587);
                account.setSmtpSsl(true);
                break;
            case "outlook":
            case "hotmail":
                account.setName("Outlook");
                account.setImapServer("outlook.office.365.com");
                account.setImapPort(993);
                account.setImapSsl(true);
                account.setSmtpServer("smtp.office.365.com");
                account.setSmtpPort(587);
                account.setSmtpSsl(true);
                break;
            case "yahoo":
                account.setName("Yahoo");
                account.setImapServer("imap.yahoo.com");
                account.setImapPort(993);
                account.setImapSsl(true);
                account.setSmtpServer("smtp.yahoo.com");
                account.setSmtpPort(587);
                account.setSmtpSsl(true);
                break;
            default:
                account.setName("custom");
                break;
        }
        return account;
    }
}
