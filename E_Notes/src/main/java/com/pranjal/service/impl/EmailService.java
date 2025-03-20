package com.pranjal.service.impl;


import com.pranjal.dto.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;


    public void send(EmailRequest emailRequest) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(mailFrom, emailRequest.getTitle());
        mimeMessageHelper.setTo(emailRequest.getTo());
        mimeMessageHelper.setSubject(emailRequest.getSubject());
        mimeMessageHelper.setText(emailRequest.getMessage());

        mailSender.send(mimeMessage);
    }

}
