package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import com.projectgl.backend.User.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "registered_library_account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RegisteredLibraryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apikey")
    private String apiKey;

    @Column(name = "creationtimestamp")
    private LocalDateTime creationTimeStamp;

    @Column(name = "updatetimestamp")
    private LocalDateTime updateTimeStamp;

    @Column(name = "type")
    private String accountType;

    public enum AccountType {
        STEAM, ORIGIN, UBISOFT, EPIC, GOG
    }

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    @OneToMany
    @JoinColumn(name = "registeredlibraryaccountid")
    private List<PersonalGameInformation> personalGameInformationList;

    public void addPersonalGameInformation(PersonalGameInformation personalGameInformation){
        this.personalGameInformationList.add(personalGameInformation);
        personalGameInformation.setRegisteredLibraryAccount(this);
    }

}
