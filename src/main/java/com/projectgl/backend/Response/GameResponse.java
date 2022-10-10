package com.projectgl.backend.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class GameResponse {

    private GameResponse.Status status;

    private String game_name;

    private Long personal_game_id;

    private Long game_id;

    private String game_description;

    private String picture_url;

    private String banner_url;

    private String libraryName;

    private Long library_id;

    public enum Status {
        SESSION_KEY_OK, USER_DOES_NOT_EXIST, SESSION_EXPIRED, NO_ACCOUNT_FOUND, ACCOUNT_ID_MISSMATCH, NO_GAME_FOUND
    }
}
