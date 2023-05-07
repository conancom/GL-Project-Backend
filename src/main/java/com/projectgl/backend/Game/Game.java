package com.projectgl.backend.Game;

import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "game", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "searchName"),
        @Index(columnList = "creationtimestamp"),
        @Index(columnList = "updatetimestamp")
}, schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Game {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String searchName;

    @Column(nullable = false)
    private String title;

    @Lob
    private String summary;

    @Lob
    private String storyline;

    private double rating;

    private int first_release_date;

    @Column(name = "profileimg")
    private String profileImg;

    @Column(name = "backgroundimg")
    private String backgroundImg;

    @OneToMany
    @JoinColumn(name = "gameid")
    private List<PersonalGameInformation> personalGameInformationList;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameVideo> gameVideos;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameScreenshot> gameScreenshots;

    @Column(name = "creationtimestamp", nullable = false, updatable = false)
    private LocalDateTime creationTimeStamp = LocalDateTime.now();

    @Column(name = "updatetimestamp", nullable = false)
    private LocalDateTime updateTimeStamp = LocalDateTime.now();

    public void addPersonalGameInformation(PersonalGameInformation personalGameInformation){
        this.personalGameInformationList.add(personalGameInformation);
        personalGameInformation.setGame(this);
    }

}
