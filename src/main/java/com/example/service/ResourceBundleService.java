package com.example.service;

import com.example.enums.AppLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourceBundleService {
    private final ResourceBundleMessageSource messageSource;

    public ResourceBundleService(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, AppLanguage lang) {
        return messageSource.getMessage(code,null, new Locale(lang.name()));
    }

}
