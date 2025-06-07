package model_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.amina.jobnotifier.model.EmailAccount;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EmailAccount Tests")
class EmailAccountTest {

    private EmailAccount emailAccount;
    private final String TEST_ID = "test-id-123";
    private final String TEST_NAME = "Test Account";
    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_PASSWORD = "6Qershor+";
    private final String TEST_IMAP_SERVER = "imap.test.com";
    private final String TEST_SMTP_SERVER = "smtp.test.com";

    @Test
    void testDefaultConstructor() {
        emailAccount = new EmailAccount();
        assertTrue(emailAccount.isActive());
        assertEquals(993, emailAccount.getImapPort());
        assertFalse(emailAccount.isImapSsl());
        assertEquals(587, emailAccount.getSmtpPort());
        assertTrue(emailAccount.isSmtpSsl());
        assertNull(emailAccount.getId());
        assertNull(emailAccount.getName());
        assertNull(emailAccount.getEmail());
        assertNull(emailAccount.getPassword());
        assertNull(emailAccount.getImapServer());
        assertNull(emailAccount.getSmtpServer());
    }

        @Test
        @DisplayName("Parameterized constructor sets provided values and defaults")
        void testParameterizedConstructor() {
            emailAccount = new EmailAccount(TEST_NAME, TEST_EMAIL, TEST_PASSWORD,
                    TEST_IMAP_SERVER, 143, true);

            // Check provided values
            assertEquals(TEST_NAME, emailAccount.getName(), "Name should be set correctly");
            assertEquals(TEST_EMAIL, emailAccount.getEmail(), "Email should be set correctly");
            assertEquals(TEST_PASSWORD, emailAccount.getPassword(), "Password should be set correctly");
            assertEquals(TEST_IMAP_SERVER, emailAccount.getImapServer(), "IMAP server should be set correctly");
            assertEquals(143, emailAccount.getImapPort(), "IMAP port should be set correctly");
            assertTrue(emailAccount.isImapSsl(), "IMAP SSL should be set correctly");

            // Check default values are still set
            assertTrue(emailAccount.isActive(), "Account should be active by default");
            assertEquals(587, emailAccount.getSmtpPort(), "Default SMTP port should be 587");
            assertTrue(emailAccount.isSmtpSsl(), "SMTP SSL should be true by default");
            assertNull(emailAccount.getId(), "ID should be null");
            assertNull(emailAccount.getSmtpServer(), "SMTP server should be null");
        }

        @Test
        @DisplayName("Parameterized constructor handles null values")
        void testParameterizedConstructorWithNulls() {
            emailAccount = new EmailAccount(null, null, null, null, 0, false);

            assertNull(emailAccount.getName(), "Name should accept null");
            assertNull(emailAccount.getEmail(), "Email should accept null");
            assertNull(emailAccount.getPassword(), "Password should accept null");
            assertNull(emailAccount.getImapServer(), "IMAP server should accept null");
            assertEquals(0, emailAccount.getImapPort(), "IMAP port should be set to 0");
            assertFalse(emailAccount.isImapSsl(), "IMAP SSL should be false");
        }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @BeforeEach
        void setUp() {
            emailAccount = new EmailAccount();
        }

        @Test
        @DisplayName("ID field getter and setter")
        void testIdGetterSetter() {
            emailAccount.setId(TEST_ID);
            assertEquals(TEST_ID, emailAccount.getId());

            emailAccount.setId(null);
            assertNull(emailAccount.getId());
        }

        @Test
        @DisplayName("Name field getter and setter")
        void testNameGetterSetter() {
            emailAccount.setName(TEST_NAME);
            assertEquals(TEST_NAME, emailAccount.getName());

            emailAccount.setName("");
            assertEquals("", emailAccount.getName());

            emailAccount.setName(null);
            assertNull(emailAccount.getName());
        }

        @Test
        @DisplayName("Email field getter and setter")
        void testEmailGetterSetter() {
            emailAccount.setEmail(TEST_EMAIL);
            assertEquals(TEST_EMAIL, emailAccount.getEmail());

            emailAccount.setEmail(null);
            assertNull(emailAccount.getEmail());
        }

        @Test
        @DisplayName("Password field getter and setter")
        void testPasswordGetterSetter() {
            emailAccount.setPassword(TEST_PASSWORD);
            assertEquals(TEST_PASSWORD, emailAccount.getPassword());

            emailAccount.setPassword(null);
            assertNull(emailAccount.getPassword());
        }

        @Test
        @DisplayName("IMAP server field getter and setter")
        void testImapServerGetterSetter() {
            emailAccount.setImapServer(TEST_IMAP_SERVER);
            assertEquals(TEST_IMAP_SERVER, emailAccount.getImapServer());

            emailAccount.setImapServer(null);
            assertNull(emailAccount.getImapServer());
        }

        @Test
        @DisplayName("IMAP port field getter and setter")
        void testImapPortGetterSetter() {
            emailAccount.setImapPort(143);
            assertEquals(143, emailAccount.getImapPort());

            emailAccount.setImapPort(0);
            assertEquals(0, emailAccount.getImapPort());

            emailAccount.setImapPort(-1);
            assertEquals(-1, emailAccount.getImapPort());
        }

        @Test
        @DisplayName("IMAP SSL field getter and setter")
        void testImapSslGetterSetter() {
            // Default is false
            assertFalse(emailAccount.isImapSsl());

            emailAccount.setImapSsl(true);
            assertTrue(emailAccount.isImapSsl());

            emailAccount.setImapSsl(false);
            assertFalse(emailAccount.isImapSsl());
        }

        @Test
        @DisplayName("SMTP server field getter and setter")
        void testSmtpServerGetterSetter() {
            emailAccount.setSmtpServer(TEST_SMTP_SERVER);
            assertEquals(TEST_SMTP_SERVER, emailAccount.getSmtpServer());

            emailAccount.setSmtpServer(null);
            assertNull(emailAccount.getSmtpServer());
        }

        @Test
        @DisplayName("SMTP port field getter and setter")
        void testSmtpPortGetterSetter() {
            // Default is 587
            assertEquals(587, emailAccount.getSmtpPort());

            emailAccount.setSmtpPort(25);
            assertEquals(25, emailAccount.getSmtpPort());

            emailAccount.setSmtpPort(0);
            assertEquals(0, emailAccount.getSmtpPort());
        }

        @Test
        @DisplayName("SMTP SSL field getter and setter")
        void testSmtpSslGetterSetter() {
            // Default is true
            assertTrue(emailAccount.isSmtpSsl());

            emailAccount.setSmtpSsl(false);
            assertFalse(emailAccount.isSmtpSsl());

            emailAccount.setSmtpSsl(true);
            assertTrue(emailAccount.isSmtpSsl());
        }

        @Test
        @DisplayName("Active field getter and setter")
        void testActiveGetterSetter() {
            // Default is true
            assertTrue(emailAccount.isActive());

            emailAccount.setActive(false);
            assertFalse(emailAccount.isActive());

            emailAccount.setActive(true);
            assertTrue(emailAccount.isActive());
        }
    }

    @Nested
    @DisplayName("getDisplayName Method Tests")
    class GetDisplayNameTests {

        @BeforeEach
        void setUp() {
            emailAccount = new EmailAccount();
        }

        @Test
        @DisplayName("getDisplayName with both name and email")
        void testGetDisplayNameWithBothFields() {
            emailAccount.setName(TEST_NAME);
            emailAccount.setEmail(TEST_EMAIL);

            String expected = TEST_NAME + " <" + TEST_EMAIL + ">";
            assertEquals(expected, emailAccount.getDisplayName());
        }

        @Test
        @DisplayName("getDisplayName with null name")
        void testGetDisplayNameWithNullName() {
            emailAccount.setName(null);
            emailAccount.setEmail(TEST_EMAIL);

            String expected = "null <" + TEST_EMAIL + ">";
            assertEquals(expected, emailAccount.getDisplayName());
        }

        @Test
        @DisplayName("getDisplayName with null email")
        void testGetDisplayNameWithNullEmail() {
            emailAccount.setName(TEST_NAME);
            emailAccount.setEmail(null);

            String expected = TEST_NAME + " <null>";
            assertEquals(expected, emailAccount.getDisplayName());
        }

        @Test
        @DisplayName("getDisplayName with both fields null")
        void testGetDisplayNameWithBothFieldsNull() {
            emailAccount.setName(null);
            emailAccount.setEmail(null);

            String expected = "null <null>";
            assertEquals(expected, emailAccount.getDisplayName());
        }

        @Test
        @DisplayName("getDisplayName with empty strings")
        void testGetDisplayNameWithEmptyStrings() {
            emailAccount.setName("");
            emailAccount.setEmail("");

            String expected = " <>";
            assertEquals(expected, emailAccount.getDisplayName());
        }
    }

    @Nested
    @DisplayName("getCommonSettings Static Method Tests")
    class GetCommonSettingsTests {

        private final String testEmail = "user@example.com";
        private final String testPassword = "password123";

        @Test
        @DisplayName("Gmail common settings")
        void testGmailCommonSettings() {
            EmailAccount account = EmailAccount.getCommonSettings("gmail", testEmail, testPassword);

            assertNotNull(account, "Account should not be null");
            assertEquals("Gmail", account.getName());
            assertEquals(testEmail, account.getEmail());
            assertEquals(testPassword, account.getPassword());
            assertEquals("imap.gmail.com", account.getImapServer());
            assertEquals(993, account.getImapPort());
            assertTrue(account.isImapSsl());
            assertEquals("smtp.gmail.com", account.getSmtpServer());
            assertEquals(587, account.getSmtpPort());
            assertTrue(account.isSmtpSsl());
            assertTrue(account.isActive());
        }

        @ParameterizedTest
        @ValueSource(strings = {"GMAIL", "Gmail", "gMaIl"})
        @DisplayName("Gmail settings with different case variations")
        void testGmailCaseInsensitive(String provider) {
            EmailAccount account = EmailAccount.getCommonSettings(provider, testEmail, testPassword);

            assertEquals("Gmail", account.getName());
            assertEquals("imap.gmail.com", account.getImapServer());
        }

        @Test
        @DisplayName("Outlook common settings")
        void testOutlookCommonSettings() {
            EmailAccount account = EmailAccount.getCommonSettings("outlook", testEmail, testPassword);

            assertEquals("Outlook", account.getName());
            assertEquals(testEmail, account.getEmail());
            assertEquals(testPassword, account.getPassword());
            assertEquals("outlook.office.365.com", account.getImapServer());
            assertEquals(993, account.getImapPort());
            assertTrue(account.isImapSsl());
            assertEquals("smtp.office.365.com", account.getSmtpServer());
            assertEquals(587, account.getSmtpPort());
            assertTrue(account.isSmtpSsl());
        }

        @Test
        @DisplayName("Hotmail common settings (same as Outlook)")
        void testHotmailCommonSettings() {
            EmailAccount account = EmailAccount.getCommonSettings("hotmail", testEmail, testPassword);

            assertEquals("Outlook", account.getName());
            assertEquals("outlook.office.365.com", account.getImapServer());
            assertEquals("smtp.office.365.com", account.getSmtpServer());
        }

        @ParameterizedTest
        @ValueSource(strings = {"OUTLOOK", "Outlook", "oUtLoOk", "HOTMAIL", "Hotmail", "hOtMaIl"})
        @DisplayName("Outlook/Hotmail settings with different case variations")
        void testOutlookHotmailCaseInsensitive(String provider) {
            EmailAccount account = EmailAccount.getCommonSettings(provider, testEmail, testPassword);

            assertEquals("Outlook", account.getName());
            assertEquals("outlook.office.365.com", account.getImapServer());
        }

        @Test
        @DisplayName("Yahoo common settings")
        void testYahooCommonSettings() {
            EmailAccount account = EmailAccount.getCommonSettings("yahoo", testEmail, testPassword);

            assertEquals("Yahoo", account.getName());
            assertEquals(testEmail, account.getEmail());
            assertEquals(testPassword, account.getPassword());
            assertEquals("imap.yahoo.com", account.getImapServer());
            assertEquals(993, account.getImapPort());
            assertTrue(account.isImapSsl());
            assertEquals("smtp.yahoo.com", account.getSmtpServer());
            assertEquals(587, account.getSmtpPort());
            assertTrue(account.isSmtpSsl());
        }

        @ParameterizedTest
        @ValueSource(strings = {"YAHOO", "Yahoo", "yAhOo"})
        @DisplayName("Yahoo settings with different case variations")
        void testYahooCaseInsensitive(String provider) {
            EmailAccount account = EmailAccount.getCommonSettings(provider, testEmail, testPassword);

            assertEquals("Yahoo", account.getName());
            assertEquals("imap.yahoo.com", account.getImapServer());
        }

        @ParameterizedTest
        @ValueSource(strings = {"unknown", "custom", "test", "", "invalid"})
        @DisplayName("Unknown provider returns custom settings")
        void testUnknownProvider(String provider) {
            EmailAccount account = EmailAccount.getCommonSettings(provider, testEmail, testPassword);

            assertEquals("custom", account.getName());
            assertEquals(testEmail, account.getEmail());
            assertEquals(testPassword, account.getPassword());
            assertNull(account.getImapServer());
            assertNull(account.getSmtpServer());
            // Default values should still be set
            assertEquals(993, account.getImapPort());
            assertEquals(587, account.getSmtpPort());
            assertTrue(account.isActive());
        }

        @Test
        @DisplayName("getCommonSettings with null parameters")
        void testGetCommonSettingsWithNulls() {
            EmailAccount account = EmailAccount.getCommonSettings("gmail", null, null);

            assertEquals("Gmail", account.getName());
            assertNull(account.getEmail());
            assertNull(account.getPassword());
            assertEquals("imap.gmail.com", account.getImapServer());
        }

        @Test
        @DisplayName("getCommonSettings with null provider")
        void testGetCommonSettingsWithNullProvider() {
            assertThrows(NullPointerException.class, () -> {
                EmailAccount.getCommonSettings(null, testEmail, testPassword);
            }, "Should throw NullPointerException when provider is null");
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Complete workflow: create, configure, and use account")
        void testCompleteWorkflow() {
            // Create account with common settings
            emailAccount = EmailAccount.getCommonSettings("gmail", TEST_EMAIL, TEST_PASSWORD);

            // Customize settings
            emailAccount.setId(TEST_ID);
            emailAccount.setName("My Gmail Account");

            // Verify all settings
            assertEquals(TEST_ID, emailAccount.getId());
            assertEquals("My Gmail Account", emailAccount.getName());
            assertEquals(TEST_EMAIL, emailAccount.getEmail());
            assertEquals(TEST_PASSWORD, emailAccount.getPassword());
            assertEquals("imap.gmail.com", emailAccount.getImapServer());
            assertEquals(993, emailAccount.getImapPort());
            assertTrue(emailAccount.isImapSsl());
            assertEquals("smtp.gmail.com", emailAccount.getSmtpServer());
            assertEquals(587, emailAccount.getSmtpPort());
            assertTrue(emailAccount.isSmtpSsl());
            assertTrue(emailAccount.isActive());

            // Test display name
            String expectedDisplay = "My Gmail Account <" + TEST_EMAIL + ">";
            assertEquals(expectedDisplay, emailAccount.getDisplayName());

            // Test deactivation
            emailAccount.setActive(false);
            assertFalse(emailAccount.isActive());
        }

        @Test
        @DisplayName("Account modification workflow")
        void testAccountModificationWorkflow() {
            // Start with default constructor
            emailAccount = new EmailAccount();

            // Configure manually
            emailAccount.setName("Custom Account");
            emailAccount.setEmail("custom@domain.com");
            emailAccount.setPassword("customPassword");
            emailAccount.setImapServer("imap.custom.com");
            emailAccount.setImapPort(143);
            emailAccount.setImapSsl(false);
            emailAccount.setSmtpServer("smtp.custom.com");
            emailAccount.setSmtpPort(25);
            emailAccount.setSmtpSsl(false);

            // Verify configuration
            assertEquals("Custom Account", emailAccount.getName());
            assertEquals("custom@domain.com", emailAccount.getEmail());
            assertEquals("imap.custom.com", emailAccount.getImapServer());
            assertEquals(143, emailAccount.getImapPort());
            assertFalse(emailAccount.isImapSsl());
            assertEquals("smtp.custom.com", emailAccount.getSmtpServer());
            assertEquals(25, emailAccount.getSmtpPort());
            assertFalse(emailAccount.isSmtpSsl());
            assertTrue(emailAccount.isActive()); // Should still be default true
        }
    }

    @Nested
    @DisplayName("Edge Cases and Boundary Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Extreme port values")
        void testExtremePortValues() {
            emailAccount = new EmailAccount();

            // Test maximum port value
            emailAccount.setImapPort(65535);
            assertEquals(65535, emailAccount.getImapPort());

            emailAccount.setSmtpPort(65535);
            assertEquals(65535, emailAccount.getSmtpPort());

            // Test minimum port value
            emailAccount.setImapPort(1);
            assertEquals(1, emailAccount.getImapPort());

            emailAccount.setSmtpPort(1);
            assertEquals(1, emailAccount.getSmtpPort());

            // Test invalid port values (negative)
            emailAccount.setImapPort(-1);
            assertEquals(-1, emailAccount.getImapPort());
        }

        @Test
        @DisplayName("Very long string values")
        void testLongStringValues() {
            emailAccount = new EmailAccount();
            String longString = "a".repeat(1000);

            emailAccount.setName(longString);
            assertEquals(longString, emailAccount.getName());

            emailAccount.setEmail(longString);
            assertEquals(longString, emailAccount.getEmail());

            emailAccount.setPassword(longString);
            assertEquals(longString, emailAccount.getPassword());
        }

        @Test
        @DisplayName("Special characters in fields")
        void testSpecialCharacters() {
            emailAccount = new EmailAccount();
            String specialChars = "!@#$%^&*()[]{}|\\:;\"'<>,.?/~`";

            emailAccount.setName(specialChars);
            assertEquals(specialChars, emailAccount.getName());

            emailAccount.setPassword(specialChars);
            assertEquals(specialChars, emailAccount.getPassword());

            // Test display name with special characters
            emailAccount.setEmail("test@example.com");
            String expectedDisplay = specialChars + " <test@example.com>";
            assertEquals(expectedDisplay, emailAccount.getDisplayName());
        }

        @Test
        @DisplayName("Multiple provider changes")
        void testMultipleProviderChanges() {
            // Start with Gmail
            emailAccount = EmailAccount.getCommonSettings("gmail", TEST_EMAIL, TEST_PASSWORD);
            assertEquals("Gmail", emailAccount.getName());

            // Change to Yahoo settings manually
            emailAccount.setName("Yahoo");
            emailAccount.setImapServer("imap.yahoo.com");
            emailAccount.setSmtpServer("smtp.yahoo.com");

            assertEquals("Yahoo", emailAccount.getName());
            assertEquals("imap.yahoo.com", emailAccount.getImapServer());
            assertEquals("smtp.yahoo.com", emailAccount.getSmtpServer());
        }
    }
}
