package com.example.entity;

import com.example.enums.Gender;
import com.example.enums.GeneralStatus;
import com.example.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.management.relation.Role;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GeneralStatus status;

    @Column(name = "brith_date")
    private LocalDateTime birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "role")
    private RoleEnum role;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "role")
    private RoleEnum roleEnum;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "role")
    private RoleEnum role;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
}
