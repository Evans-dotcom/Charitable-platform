package com.example.farajaplatform.exception;

public class UserNotVerifiedException extends Exception{
    /** Did we send a new email? */
    private boolean newEmailSent;

    /**
     * Constructor.
     * @param newEmailSent Was a new email sent?
     */
    public UserNotVerifiedException(boolean newEmailSent) {
        this.newEmailSent = newEmailSent;
    }

    /**
     * Was a new email sent?
     * @return True if it was, false otherwise.
     */
    public boolean isNewEmailSent() {
        return newEmailSent;
    }
}

