package com.example.service;

import com.example.entity.EmailHistoryEntity;
import com.example.enums.AppLanguage;
import com.example.enums.SmsType;
import com.example.exp.AppBadException;
import com.example.repository.EmailHistoryRepository;
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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.domain}")
    private String serverDomain;

    private final ResourceBundleService resourceBundleService;
    private final EmailHistoryService emailHistoryService;
    private final EmailHistoryRepository emailHistoryRepository;
    private final JavaMailSender mailSender;

    public EmailSendingService(ResourceBundleService resourceBundleService, EmailHistoryService emailHistoryService, EmailHistoryRepository emailHistoryRepository, JavaMailSender mailSender) {
        this.resourceBundleService = resourceBundleService;
        this.emailHistoryService = emailHistoryService;
        this.emailHistoryRepository = emailHistoryRepository;
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(String email, String profileId, AppLanguage lang) {
        String subject = "Complete registration";
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <style>\n" +
                "        .button:hover {\n" +
                "            background-color: #dd4444;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Complete registration verification</h1>\n" +
                "<p>Please click to button for completing registration: <a style=\"padding: 10px 30px;\n" +
                "display: inline-block;\n" +
                "text-decoration: none;\n" +
                "collapse: white;\n" +
                "background-color:  indianred;\" href=\"%s/api/v1/auth/verification/%s?lang=%s\"\n" +
                "target=\"_blank\"> Click\n" +
                "    there</a></p>\n" +
                "</body>\n" +
                "</html>";
        body = String.format(body, serverDomain, JwtUtil.encode(profileId, email), lang);
        sendMimeEmail(email, subject, body);
    }
   /* public void sendRegistration(String username, AppLanguage language) {
        String subject = "Complete registration";
        String body = RandomUtil.getRandomCode();
        emailHistoryService.create(username,body, SmsType.REGISTRATION);
        sendSimpleEmail(username, subject, body);
    }*/
// We will continue this code in the reset API
    public void sentResetPasswordEmail(String username, AppLanguage language) {
        String code = RandomUtil.getRandomCode();
        String subject = "Reset password Conformation";
        String body = "How are you. This is confirm code reset password send code: " + code;
        checkAndSendMineEmail(username, subject, body,code,language);
    }

    private void checkAndSendMineEmail(String email, String subject, String body, String code, AppLanguage language) {
        Long count = emailHistoryService.getEmailCount(email);
        if (count >= 3) {
            throw new AppBadException(resourceBundleService.getMessage("email.reached.sms", language));
        }

        sendMimeEmail(email, subject, body);
        emailHistoryService.create(email, code, SmsType.RESET_PASSWORD);
    }

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

   /* public void send(String toAccount, String subject, String text) {
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
    }*/
}
