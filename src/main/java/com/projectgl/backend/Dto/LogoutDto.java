package com.projectgl.backend.Dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogoutDto {

    private String session_id;

}
