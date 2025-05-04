package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    Optional<Object> findByIdAndVisibleTrue(String id);

    @NotNull
    Optional<ProfileEntity> findById(@NotNull String id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity  set status=?2 where id=?1")
    void changeStatus(String profileId, GeneralStatus generalStatus);

    @Query("from ProfileEntity where email=?1")
    Optional<ProfileEntity> findByEmail(String username);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set password=?2 where id =?1")
    void updatePassword(String id, String encode);
}
