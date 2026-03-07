package com.store.application.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String to,String subject,String htmlBody) throws Exception{

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message,true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody,true);

        mailSender.send(message);
    }
}