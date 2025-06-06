package model_tests;

import com.amina.jobnotifier.model.EmailMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EmailMessageTests {
    private EmailMessage emailMessage;
    private String test_from = "test@example.com";
    private String test_subject = "Test Subject";
    private String test_content = "Test Content";
    private LocalDateTime test_date = LocalDateTime.of(2025, 6, 6, 10, 30, 0);


    @Test
    public void testConstructor_default(){
        emailMessage = new EmailMessage();
        assertFalse(emailMessage.isRead());
        assertEquals(EmailMessage.RejectionCategory.UNPROCESSED, emailMessage.getCategory());
        assertNull(emailMessage.getFrom());
        assertNull(emailMessage.getSubject());
        assertNull(emailMessage.getContent());
        assertNull(emailMessage.getReceivedDate());
    }

    @Test
    public void testConstructor_withParam(){
        emailMessage = new EmailMessage(test_from, test_subject, test_content);
        assertEquals(test_from, emailMessage.getFrom());
        assertEquals(test_subject, emailMessage.getSubject());
        assertEquals(test_content, emailMessage.getContent());
        assertFalse(emailMessage.isRead());
        assertEquals(EmailMessage.RejectionCategory.UNPROCESSED, emailMessage.getCategory());
        assertNull(emailMessage.getReceivedDate());
    }

    @Test
    void testConstructor_nullParam(){
        emailMessage = new EmailMessage(null, null, null);
        assertNull(emailMessage.getFrom());
        assertNull(emailMessage.getSubject());
        assertNull(emailMessage.getContent());
    }

    @Nested
    class GetterAndSetterTests {
        @BeforeEach
        void setUp() {
            emailMessage = new EmailMessage();
        }

        @Test
        void testGetterSetter_from(){
            emailMessage.setFrom(test_from);
            assertEquals(test_from, emailMessage.getFrom());
            emailMessage.setFrom(null);
            assertNull(emailMessage.getFrom());
        }

        @Test
        void testGetterSetter_subject(){
            emailMessage.setSubject(test_subject);
            assertEquals(test_subject, emailMessage.getSubject());
            emailMessage.setSubject(null);
            assertNull(emailMessage.getSubject());
        }

        @Test
        void testGetterSetter_content(){
            emailMessage.setContent(test_content);
            assertEquals(test_content, emailMessage.getContent());
            emailMessage.setContent(null);
            assertNull(emailMessage.getContent());
        }

        @Test
        void testGetterSetter_receivedDate(){
            emailMessage.setReceivedDate(test_date);
            assertEquals(test_date, emailMessage.getReceivedDate());
            emailMessage.setReceivedDate(null);
            assertNull(emailMessage.getReceivedDate());
        }

        @Test
        void testGetterSetter_read(){
            assertFalse(emailMessage.isRead());
            emailMessage.setRead(true);
            assertTrue(emailMessage.isRead());
            emailMessage.setRead(false);
            assertFalse(emailMessage.isRead());
        }

        @Test
        void testGetterSetter_category(){
            assertEquals(EmailMessage.RejectionCategory.UNPROCESSED, emailMessage.getCategory());
            emailMessage.setCategory(EmailMessage.RejectionCategory.REJECTION);
            assertEquals(EmailMessage.RejectionCategory.REJECTION, emailMessage.getCategory());
            emailMessage.setCategory(EmailMessage.RejectionCategory.POTENTIAL_REJECTION);
            assertEquals(EmailMessage.RejectionCategory.POTENTIAL_REJECTION, emailMessage.getCategory());
            emailMessage.setCategory(EmailMessage.RejectionCategory.OTHER);
            assertEquals(EmailMessage.RejectionCategory.OTHER, emailMessage.getCategory());
            emailMessage.setCategory(null);
            assertNull(emailMessage.getCategory());
        }
    }

    @Test
    void testCompleteWorkflow(){
        emailMessage = new EmailMessage(test_from, test_subject, test_content);
        emailMessage.setReceivedDate(test_date);
        emailMessage.setRead(true);
        emailMessage.setCategory(EmailMessage.RejectionCategory.REJECTION);

        assertEquals(test_from, emailMessage.getFrom());
        assertEquals(test_subject, emailMessage.getSubject());
        assertEquals(test_content, emailMessage.getContent());
        assertEquals(test_date, emailMessage.getReceivedDate());
        assertTrue(emailMessage.isRead());
        assertEquals(EmailMessage.RejectionCategory.REJECTION, emailMessage.getCategory());

        String expectedString = "EmailMessage [from=" + test_from +
                ", subject=" + test_subject +
                ", content=" + test_content + "]";
        assertEquals(expectedString, emailMessage.toString());
    }

    @Test
    void testDisplayNames(){
        assertEquals("Rejection", EmailMessage.RejectionCategory.REJECTION.getDisplayName());
        assertEquals("Potential Rejection", EmailMessage.RejectionCategory.POTENTIAL_REJECTION.getDisplayName());
        assertEquals("Other", EmailMessage.RejectionCategory.OTHER.getDisplayName());
        assertEquals("Unprocessed", EmailMessage.RejectionCategory.UNPROCESSED.getDisplayName());
    }
}
