package com.projectgl.backend.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginDto {

    private String username_email;

    private String password;
}
