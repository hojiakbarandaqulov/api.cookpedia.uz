package com.example.entity;

import com.example.enums.Gender;
import com.example.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "full_name",nullable = false)
    private String fullName;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name ="password")
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

    @Column(name = "visible")
    private Boolean visible=Boolean.TRUE;

}
