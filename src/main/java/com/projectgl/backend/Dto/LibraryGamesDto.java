package com.projectgl.backend.Dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryGamesDto {

    private String session_id;

    private long library_id;

}
