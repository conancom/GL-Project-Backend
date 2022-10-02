package com.projectgl.backend.Response;

import com.projectgl.backend.Dto.LibraryAccountDetails;
import lombok.Builder;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Setter
public class AllLibraryGamesResponse {
    private AllLibraryGamesResponse.Status status;

    private ArrayList<LibraryAccountDetails> libraries;

    public enum Status {
        SESSION_KEY_OK, USER_DOES_NOT_EXIST, SESSION_EXPIRED
    }
}
