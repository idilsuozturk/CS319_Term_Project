package com.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailService {

    // Need an actual mailSender
    // Todo
    // @Autowired

    public void sendEmail(String email, String header, String message) {
        try {
            // Ffor now
            System.out.println("Sending email to: " + email);
            System.out.println("Subject: " + header);
            System.out.println("Message: " + message);

        } catch (Exception e) {
            // Log any of the error
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}