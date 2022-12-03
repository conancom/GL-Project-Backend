package com.projectgl.backend.PersonalGameInformation;

import com.projectgl.backend.Game.Game;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "personalgameinformation")
public class PersonalGameInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registeredlibraryaccountid")
    RegisteredLibraryAccount registeredLibraryAccount;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameid")
    Game game;

    @Column(name = "creationtimestamp")
    private LocalDateTime creationTimeStamp;

    @Column(name = "updatetimestamp")
    private LocalDateTime updateTimeStamp;
}
