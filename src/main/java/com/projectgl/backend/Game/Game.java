package com.projectgl.backend.Game;

import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "game")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String title;

    private String information;

    @Column(name = "profileimg")
    private String profileImg;

    @Column(name = "backgroundimg")
    private String backgroundImg;

    @OneToMany
    @JoinColumn(name = "gameid")
    private List<PersonalGameInformation> personalGameInformationList;

    @Column(name = "creationtimestamp")
    private LocalDateTime creationTimeStamp;

    @Column(name = "updatetimestamp")
    private LocalDateTime updateTimeStamp;

    public void addPersonalGameInformation(PersonalGameInformation personalGameInformation){
        this.personalGameInformationList.add(personalGameInformation);
        personalGameInformation.setGame(this);
    }

}
