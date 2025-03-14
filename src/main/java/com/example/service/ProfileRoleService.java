package com.example.service;


import com.example.entity.ProfileRoleEntity;
import com.example.enums.RoleEnum;
import com.example.repository.ProfileRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ProfileRoleService {

    private ProfileRoleRepository profileRoleRepository;

    public void create(String profileId, RoleEnum roleEnum) {
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRoles(roleEnum);
        entity.setCreatedDate(LocalDateTime.now());
        profileRoleRepository.save(entity);
    }

    public void delete(String profileId) {
        profileRoleRepository.deleteByProfileId(profileId);
    }
}
