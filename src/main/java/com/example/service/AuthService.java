package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.auth.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.GeneralStatus;
import com.example.enums.RoleEnum;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.repository.ProfileRoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {
    private ProfileRepository profileRepository;
    private ProfileRoleService profileRoleService;
    private ResourceBundleService messageService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   /* public ApiResponse<String> registration(RegistrationDTO dto, AppLanguage language) {
        Optional<ProfileEntity> profile = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (profile.isPresent()) {
            ProfileEntity profileEntity = profile.get();
            if (profileEntity.getStatus().equals(GeneralStatus.REGISTRATION)){
               profileRoleService.delete(profileEntity.getId());
               profileRepository.delete(profileEntity);
            }else {
                throw new AppBadException(messageService.getMessage("profile.already.exists", language));
            }
        }
        ProfileEntity entity=new ProfileEntity();
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        profileRoleService.create(entity.getId(), RoleEnum.ROLE_USER);

        return ;
    }*/
}
