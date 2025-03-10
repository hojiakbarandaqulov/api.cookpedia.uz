package com.example.entity;

import com.example.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Table(name = "profile_role")
@Entity
@Data
public class ProfileRoleEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "profile_id")
    private String profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private RoleEnum roles;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
