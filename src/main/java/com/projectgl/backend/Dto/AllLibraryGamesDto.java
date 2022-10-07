package com.projectgl.backend.Dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class AllLibraryGamesDto {

    @NotNull
    private String session_id;
}
