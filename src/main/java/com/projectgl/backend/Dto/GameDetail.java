package com.projectgl.backend.Dto;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GameDetail {

    private String game_name;

    private Long personal_game_id;

    private Long game_id;

    private String game_description;

    private String picture_url;

    private String banner_url;

    private String libraryName;

    private Long library_id;

}

