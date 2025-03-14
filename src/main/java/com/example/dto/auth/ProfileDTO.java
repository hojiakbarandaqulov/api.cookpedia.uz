package com.example.dto.auth;

import com.example.enums.RoleEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ProfileDTO {
    private String fullName;
    private String email;
    private RoleEnum roleEnum;
    private String jwt;


}
