package com.projectgl.backend.Response;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameVideoResponse {

    private String name;

    private String video_id;
}
