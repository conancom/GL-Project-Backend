package com.projectgl.backend.Response;

import com.projectgl.backend.Game.GameScreenshot;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Setter
@Getter
public class GameResponse {

    private GameResponse.Status status;

    private String game_name;

    private Long personal_game_id;

    private Long game_id;

    private String summary;

    private String storyline;

    private double rating;

    private int first_release_date;

    private String picture_url;

    private String banner_url;

    private String library_name;

    private Long library_id;

    private ArrayList<GameVideoResponse> videos;

    private ArrayList<GameScreenshotResponse> screenshots;

    public enum Status {
        SESSION_KEY_OK, USER_DOES_NOT_EXIST, SESSION_EXPIRED, NO_ACCOUNT_FOUND, ACCOUNT_ID_MISSMATCH, NO_GAME_FOUND
    }
}
