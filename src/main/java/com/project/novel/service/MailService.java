package com.project.novel.service;

import com.project.novel.util.MailAuthentication;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService  {
    private final JavaMailSender javaMailSender;
    private final MailAuthentication mailAuthentication;
    public void sendAuthMail(String userEmail) throws MessagingException {
        MimeMessage message = mailAuthentication.createMail(userEmail);
        javaMailSender.send(message);
    }
}
