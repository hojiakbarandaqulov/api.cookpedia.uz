package com.example.service;

import com.example.entity.EmailHistoryEntity;
import com.example.enums.AppLanguage;
import com.example.enums.SmsType;
import com.example.exp.AppBadException;
import com.example.repository.EmailHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class EmailHistoryService {
    private final EmailHistoryRepository emailHistoryRepository;
    private final ResourceBundleService bundleService;

    public EmailHistoryService(EmailHistoryRepository emailHistoryRepository, ResourceBundleService bundleService) {
        this.emailHistoryRepository = emailHistoryRepository;
        this.bundleService = bundleService;
    }

    public void create(String email, String code, SmsType emailType){
        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setEmail(email);
        emailHistoryEntity.setCode(code);
        emailHistoryEntity.setEmailType(emailType);
        emailHistoryEntity.setAttemptCount(0);
        emailHistoryEntity.setCreatedDate(LocalDateTime.now());
        emailHistoryRepository.save(emailHistoryEntity);
    }

    public Long getEmailCount(String email){
        LocalDateTime now = LocalDateTime.now();
        return emailHistoryRepository.countByEmailAndCreatedDateBetween(email,now.minusMinutes(1),now);
    }

    public void check(String email, String code, AppLanguage language){
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findTop1ByEmailOrderByCreatedDateDesc(email);

        if(optional.isEmpty()){
            throw  new AppBadException(bundleService.getMessage("verification.failed",language));
        }
        EmailHistoryEntity entity = optional.get();

        if (entity.getAttemptCount()>=3){
            throw  new AppBadException(bundleService.getMessage("verification.failed",language));
        }

        if (!entity.getCode().equals(code)){
            emailHistoryRepository.updateAttemptCount(entity.getId());
            throw  new AppBadException(bundleService.getMessage("verification.failed",language));
        }

        LocalDateTime expDate=entity.getCreatedDate().plusMinutes(2);
        if (LocalDateTime.now().isAfter(expDate)){
            throw  new AppBadException(bundleService.getMessage("verification.failed",language));
        }
    }
}
