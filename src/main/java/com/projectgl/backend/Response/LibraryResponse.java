package com.projectgl.backend.Response;

import com.projectgl.backend.Dto.LibraryDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Setter
@Getter
public class LibraryResponse {

    private LibraryResponse.Status status;

    private ArrayList<LibraryDetail> libraries;

    public enum Status {
        SESSION_KEY_OK, USER_DOES_NOT_EXIST, SESSION_EXPIRED, NO_ACCOUNT_FOUND, ACCOUNT_ID_MISSMATCH, STEAM_ACCOUNT_NOT_FOUND, ADDED_SUCCESSFUL
    }
}
