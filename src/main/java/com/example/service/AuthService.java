package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.auth.*;
import com.example.entity.EmailHistoryEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.GeneralStatus;
import com.example.enums.RoleEnum;
import com.example.exp.AppBadException;
import com.example.repository.EmailHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    private final ProfileRepository profileRepository;
    private final ResourceBundleService messageService;
    private final EmailSendingService emailSendingService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailHistoryRepository emailHistoryRepository;

    public AuthService(ProfileRepository profileRepository, ResourceBundleService messageService, EmailSendingService emailSendingService, BCryptPasswordEncoder bCryptPasswordEncoder, EmailHistoryRepository emailHistoryRepository) {
        this.profileRepository = profileRepository;
        this.messageService = messageService;
        this.emailSendingService = emailSendingService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailHistoryRepository = emailHistoryRepository;
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
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTRATION);
        entity.setVisible(true);
        entity.setRole(RoleEnum.ROLE_USER);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        emailSendingService.sendRegistrationEmail(dto.getEmail(),entity.getId(), language);
        return ApiResponse.ok(messageService.getMessage("registration.successful", language));
    }

    public ApiResponse<?> login(LoginDTO dto, AppLanguage language) {
        Optional<ProfileEntity> loginProfile = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (loginProfile.isEmpty()) {
            throw new AppBadException(messageService.getMessage("profile.password.wrong", language));
        }
        ProfileEntity entity = loginProfile.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), entity.getPassword())) {
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

    public ApiResponse<String> verification(String userId, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            log.warn("User not found => {}", userId);
            String message = messageService.getMessage("user.not.found", language);
            throw new AppBadException(message);
        }
        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(GeneralStatus.REGISTRATION)) {
            String message = messageService.getMessage("registration.not.completed", language);
            throw new AppBadException(message);
        }
        profileRepository.changeStatus(userId, GeneralStatus.ACTIVE);
        return ApiResponse.ok(messageService.getMessage("success", language));
    }

    public ApiResponse<String> resetPassword(ResetPasswordDTO dto, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isEmpty()) {
            log.warn("User not found => {}", dto.getEmail());
            throw new AppBadException(messageService.getMessage("profile.password.wrong", language));
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.warn("User status wrong => {}", dto.getEmail());
            throw new AppBadException(messageService.getMessage("profile.status.wrong", language));
        }

        emailSendingService.sentResetPasswordEmail(entity.getEmail(), language);
        return ApiResponse.ok(messageService.getMessage("resent.code.sent", language));
    }

    public ApiResponse<?> updatePassword(UpdatePasswordDTO dto, AppLanguage language) {
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findByEmailAndCode(dto.getUsername(),dto.getConfirmPassword());
        if (optional.isEmpty()) {
            log.warn("User not found => {}", dto.getUsername());
            throw new AppBadException(messageService.getMessage("emailHistory.not.found", language));
        }
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(dto.getUsername());
        if (byEmail.isEmpty()) {
            log.warn("User not found => {}", dto.getUsername());
            throw new AppBadException(messageService.getMessage("user.not.found", language));
        }
        ProfileEntity entity = byEmail.get();
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
        profileRepository.save(entity);
        return ApiResponse.ok(messageService.getMessage("success", language));
    }
}
