package com.application.spring.model.dto;

import com.application.spring.model.entity.RoleEnum;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UsersDto {

    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<RoleEnum> roles;
}

