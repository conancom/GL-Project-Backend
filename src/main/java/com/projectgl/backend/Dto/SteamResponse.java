package com.projectgl.backend.Dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SteamResponse {

    private int game_count;

    private ArrayList<SteamResponseGame> games;

}
