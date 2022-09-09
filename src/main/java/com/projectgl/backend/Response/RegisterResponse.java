package com.projectgl.backend.Response;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegisterResponse {

    private RegisterResponse.Status status;
    private String username;

    public enum Status {
        SUCCESS, DUPLICATE_EMAIL, DUPLICATE_USERNAME
    }
}
