package com.projectgl.backend.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class LoginDto {

    @NotBlank(message = "Cannot be empty")
    private String username_email;

    @NotBlank(message = "Cannot be empty")
    private String password;
}
