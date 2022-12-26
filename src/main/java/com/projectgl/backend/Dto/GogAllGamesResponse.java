package com.projectgl.backend.Dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GogAllGamesResponse {

    ArrayList<Integer> owned;

}
