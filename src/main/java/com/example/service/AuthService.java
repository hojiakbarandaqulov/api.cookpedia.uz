package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.auth.*;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Slf4j
@Service
public class AuthService {
    private final ProfileRepository profileRepository;
    private final ResourceBundleService messageService;
    private final EmailSendingService emailSendingService;
    //    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthService(ProfileRepository profileRepository, ResourceBundleService messageService, EmailSendingService emailSendingService) {
        this.profileRepository = profileRepository;
        this.messageService = messageService;
        this.emailSendingService = emailSendingService;
    }

    public ApiResponse<String> registration(RegistrationDTO dto, AppLanguage language) {
        Optional<ProfileEntity> profile = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (profile.isPresent()) {
            ProfileEntity profileEntity = profile.get();
            if (profileEntity.getStatus().equals(GeneralStatus.REGISTRATION)){
               profileRepository.delete(profileEntity);
            }else {
                throw new AppBadException(messageService.getMessage("profile.already.exists", language));
            }
        }
        ProfileEntity entity=new ProfileEntity();
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTRATION);
        entity.setVisible(true);
        entity.setRole(RoleEnum.ROLE_USER);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        emailSendingService.sendRegistration(dto.getEmail(), language);
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

        emailSendingService.sendRegistration(entity.getEmail(), language);
        return ApiResponse.ok(messageService.getMessage("resent.code.sent", language));
    }

    public ApiResponse<?> updatePassword(UpdatePasswordDTO dto, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getUsername());
        if (optional.isEmpty()) {
            log.warn("User not found => {}", dto.getUsername());
            throw new AppBadException(messageService.getMessage("profile.not.found", language));
        }
        ProfileEntity entity = optional.get();
        ProfileEntity  profileEntity= new ProfileEntity();
        profileEntity.setPassword(dto.getPassword());

    }
}
