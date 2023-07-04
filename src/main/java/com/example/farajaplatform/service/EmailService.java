package com.example.farajaplatform.service;

import com.example.farajaplatform.exception.EmailFailureException;
import com.example.farajaplatform.model.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /** The from address to use on emails. */
    @Value("${email.from}")
    private String fromAddress;
    /** The url of the front end for links. */
    @Value("${app.frontend.url}")
    private String url;
    /** The JavaMailSender instance. */
    private JavaMailSender javaMailSender;

    /**
     * Constructor for spring injection.
     * @param javaMailSender
     */
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Makes a SimpleMailMessage for sending.
     * @return The SimpleMailMessage created.
     */
    private SimpleMailMessage makeMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    /**
     * Sends a verification email to the user.
     * @param verificationToken The verification token to be sent.
     * @throws EmailFailureException Thrown if are unable to send the email.
     */
    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationToken.getPerson().getEmail());
        message.setSubject("Verify your email to active your account.");
        message.setText("Please follow the link below to verify your email to active your account.\n" +
                url + "/auth/verify?token=" + verificationToken.getToken());
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }


}
