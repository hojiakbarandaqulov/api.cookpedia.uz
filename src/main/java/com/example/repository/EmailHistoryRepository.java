package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import com.example.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity, String> {
    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

    @Query(value = "select * from email_history where email=?1 order by created_date=desc limit 1", nativeQuery = true)
    Optional<EmailHistoryEntity> findTop1ByEmailOrderByCreatedDateDesc(String email);

    @Modifying
    @Transactional
    @Query("update EmailHistoryEntity set attemptCount = coalesce(attemptCount,0)+1 where  id=?1")
    void updateAttemptCount(String id);

    Optional<EmailHistoryEntity> findByEmail(String toEmail);

    @Query(value = "select * from email_history where email=?1 and code=?2 order by created_date desc limit 1", nativeQuery = true)
    Optional<EmailHistoryEntity> findByEmailAndCode(String username, String confirmPassword);
}
