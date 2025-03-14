package com.example.service;

import com.example.enums.AppLanguage;
import com.example.util.JwtUtil;
import com.example.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.domain}")
    private String serverDomain;

    private final ResourceBundleMessageSource resourceBundleService;
    private final JavaMailSender mailSender;

    public EmailSendingService(ResourceBundleMessageSource resourceBundleService, JavaMailSender mailSender) {
        this.resourceBundleService = resourceBundleService;
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(String email, AppLanguage lang) {
        String subject = "Complete registration";
        String body=RandomUtil.getRandomCode();
        sendSimpleEmail(email, subject, body);
    }
// We will continue this code in the reset API
   /* public void sentResetPasswordEmail(String username, AppLanguage language) {
        String code = RandomUtil.getRandomCode();
        String subject = "Reset password Conformation";
        String body = "How are you. This is confirm code reset password send code %s : " + code;
        checkAndSendMineEmail(username, subject, body,code,language);

    }

    private void checkAndSendMineEmail(String email, String subject, String body, String code, AppLanguage language) {
        Long count = emailHistoryService.getEmailCount(email);
        if (count >= 3) {
            throw new AppBadException(resourceBundleService.getMessage("email.reached.sms", language));
        }

        sendMimeEmail(email, subject, body);
        emailHistoryService.create(email, code, SmsType.RESET_PASSWORD);

    }*/

    private void sendMimeEmail(String email, String subject, String body) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            CompletableFuture.runAsync(() -> {
                mailSender.send(msg);
            });
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String toAccount, String subject, String text)  {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendSimpleEmail(String email, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAccount);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);
    }
}
