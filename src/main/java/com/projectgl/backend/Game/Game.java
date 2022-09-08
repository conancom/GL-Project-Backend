package com.projectgl.backend.Game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String title;

    private String information;

    private String profileImg;

    private String backgroudImg;

    private LocalDateTime creationTimeStamp;

    private LocalDateTime updateTimeStamp;

}
