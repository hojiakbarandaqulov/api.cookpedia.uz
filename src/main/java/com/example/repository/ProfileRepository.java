package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    Optional<Object> findByIdAndVisibleTrue(String id);

    Optional<ProfileEntity> findById(String id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity  set status=?2 where id=?1")
    void changeStatus(String profileId, GeneralStatus generalStatus);

}
