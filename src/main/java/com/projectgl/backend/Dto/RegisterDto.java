package com.projectgl.backend.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterDto {

    private String username;

    private String email;

    private String password;
}
