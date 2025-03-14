package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.auth.LoginDTO;
import com.example.dto.auth.ProfileDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.GeneralStatus;
import com.example.enums.RoleEnum;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService {
    private final ProfileRepository profileRepository;
    private final ResourceBundleService messageService;
    //    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSendingService mailSendingService;

    public AuthService(ProfileRepository profileRepository, ResourceBundleService messageService, EmailSendingService mailSendingService) {
        this.profileRepository = profileRepository;
        this.messageService = messageService;
        this.mailSendingService = mailSendingService;
    }

    public ApiResponse<String> registration(RegistrationDTO dto, AppLanguage language) {
        Optional<ProfileEntity> profile = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (profile.isPresent()) {
            ProfileEntity profileEntity = profile.get();
            if (profileEntity.getStatus().equals(GeneralStatus.REGISTRATION)) {
                profileRepository.delete(profileEntity);
            } else {
                throw new AppBadException(messageService.getMessage("profile.already.exists", language));
            }
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTRATION);
        entity.setVisible(true);
        entity.setRole(RoleEnum.ROLE_USER);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        mailSendingService.sendRegistrationEmail(dto.getEmail(), language);
        return ApiResponse.ok(messageService.getMessage("registration.successful", language));
    }


    public ApiResponse<?> login(LoginDTO dto, AppLanguage language) {
        Optional<ProfileEntity> loginProfile = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (loginProfile.isEmpty()) {
            throw new AppBadException(messageService.getMessage("profile.password.wrong", language));
        }
        ProfileEntity entity = loginProfile.get();
        if (!entity.getPassword().equals(MD5Util.getMD5(dto.getPassword()))) {
            throw new AppBadException(messageService.getMessage("password.wrong", language));
        }
        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException(messageService.getMessage("profile.status.wrong", language));
        }
        ProfileDTO response = new ProfileDTO();
        response.setFullName(entity.getFullName());
        response.setEmail(entity.getEmail());
        response.setRoleEnum(entity.getRole());
        response.setJwt(JwtUtil.encode(response.getEmail(), entity.getId(), response.getRoleEnum()));
        return ApiResponse.ok(response);
    }
}
