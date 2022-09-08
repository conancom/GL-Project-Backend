package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "registered_library_account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisteredLibraryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiKey;

    private LocalDateTime creationTimeStamp;

    private LocalDateTime updateTimeStamp;

    private AccountType accountType;
    public enum AccountType {
        STEAM,
        ORIGIN,
        UBISOFT,
        EPIC,
        GOG;
    }

    @OneToMany
    @JoinColumn(name = "registeredlibraryaccountid")
    private List<PersonalGameInformation> personalGameInformationList;

}
