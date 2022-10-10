package com.projectgl.backend.Dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

    private String session_id;

    private long game_id;

}
