package com.example.service;

import com.example.entity.ProfileEntity;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileEntity getById(String id) {
       /* Optional<ProfileEntity> optional = profileRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()){
            throw new AppBadException("Profile not found");
        }
        return optional.get();*/
        return (ProfileEntity) profileRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }
}
