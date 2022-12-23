package com.projectgl.backend.Game;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_video")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GameVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String video_id;

    @Column(name = "creationtimestamp")
    private LocalDateTime creationTimeStamp;

    @Column(name = "updatetimestamp")
    private LocalDateTime updateTimeStamp;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    Game game;

}
