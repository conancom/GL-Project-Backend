package com.projectgl.backend.Dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GameVideo {

    private String name;

    private String video_id;
}
