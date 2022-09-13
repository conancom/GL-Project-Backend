package com.projectgl.backend.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponse {

    private LoginResponse.Status status;

    private String username;

    private String session_id;

    public enum Status {
        SUCCESS, INVALID_EMAIL, INVALID_USERNAME, INVALID_PASSWORD
    }
}
