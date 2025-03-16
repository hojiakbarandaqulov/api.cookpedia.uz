package com.example.dto;

import com.example.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDTO {
    private String id;
    private String username;
    private RoleEnum role;

    public JwtDTO(String id, String userName, RoleEnum role) {
        this.id = id;
        this.username = userName;
        this.role = role;
    }

    public JwtDTO(String id) {
        this.id = id;
    }
}
