package com.projectgl.backend.PersonalGameInformation;

import com.projectgl.backend.Game.Game;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "personalgameinformation")
public class PersonalGameInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    RegisteredLibraryAccount registeredLibraryAccount;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    Game game;

    private LocalDateTime creationTimeStamp;

    private LocalDateTime updateTimeStamp;
}
