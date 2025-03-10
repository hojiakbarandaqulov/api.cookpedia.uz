package com.example.repository;

import com.example.entity.ProfileRoleEntity;
import com.example.enums.RoleEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileRoleRepository extends JpaRepository<ProfileRoleEntity,Integer> {
    @Transactional
    @Modifying
    void deleteByProfileId(String profileId);

    @Query("select p.roles from ProfileRoleEntity p where p.profileId=?1")
    List<RoleEnum> getAllRolesListByProfileId(String profileId);

}
