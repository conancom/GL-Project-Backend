package com.projectgl.backend.Game;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_screenshot")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GameScreenshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String screenshot_url;

    @Column(name = "creationtimestamp")
    private LocalDateTime creationTimeStamp;

    @Column(name = "updatetimestamp")
    private LocalDateTime updateTimeStamp;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    Game game;
}
