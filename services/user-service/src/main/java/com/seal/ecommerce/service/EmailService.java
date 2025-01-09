package com.seal.ecommerce.service;

import com.seal.ecommerce.email.EmailTemplateName;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to,
       String username,
       EmailTemplateName emailTemplate,
       String confirmationUrl,
       String activationCode,
       String subject) throws MessagingException;
}
