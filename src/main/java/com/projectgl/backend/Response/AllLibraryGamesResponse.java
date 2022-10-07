package com.projectgl.backend.Response;

import com.projectgl.backend.Dto.GameDetail;
import com.projectgl.backend.Dto.LibraryAccountDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Setter
@Getter
public class AllLibraryGamesResponse {
    private AllLibraryGamesResponse.Status status;

    private ArrayList<GameDetail> gameDetails;

    public enum Status {
        SESSION_KEY_OK, USER_DOES_NOT_EXIST, SESSION_EXPIRED
    }
}
