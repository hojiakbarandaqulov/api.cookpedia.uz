package com.example.service;

import com.example.enums.AppLanguage;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourceBundleService {
    private final ResourceBundleMessageSource resourceBundle;

    public ResourceBundleService(ResourceBundleMessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
    public String getMessage(String code, AppLanguage lang) {
        return resourceBundle.getMessage(code,null, new Locale(lang.name()));
    }
}
