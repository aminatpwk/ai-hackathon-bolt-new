# Email AI Notifier

This project is being developed as part of an AI Hackathon. The goal is to build a smart email monitoring system that can scan job-related emails, extract relevant information, and send custom notifications to the user based on preferences.

---

## Current Status

> This is an early-stage prototype. The following features are **already implemented**:

###  Basic Email Fetching (Gmail IMAP)
- Connects to a Gmail inbox using JavaMail API.
- Fetches the 5 most recent emails from the inbox.
- Extracts and prints:
    - Sender (From)
    - Subject line
    - Plain text content (if available)
- Console-based display for debugging and iteration.

### Rejection Detection With Notifications (draft I)
- Uses heuristic pattern matching to detect job rejection emails.
- Analyzes:
    - Common rejection phrases
    - Keyword frequency
    - HR/recruiter sender domains
- Emails are categorized as either REJECTION or OTHER.
- Desktop notifications (via NotificationUtil) are triggered when a rejection is detected, helping
users stay informed immediately.

### Email Classification via OpenAI
- Each incoming email is sent to the OpenAI API for natural language analysis. The model extracts key information such as:
   - Subject
   - Company
   - Category (REJECTION, OTHER)
Only emails classified as REJECTION trigger a system notification.
  
---

## Tech Stack

- Java 17+
- JavaMail API
- [dotenv-java](https://github.com/cdimascio/dotenv-java) for env management
- Maven (for dependency management and build)

---

##  Project Structure
```
com.amina.jobnotifier/
├── app/                     # Main application entry point
├── controller/              # Handles user-level actions
├── service/                 # Email logic (fetching, parsing, classification)
├── model/                   # Email message and email account model
└── util/                    # MIME/text decoding helpers and notification helpers
```

## A/N
More features and AI integration coming soon!
