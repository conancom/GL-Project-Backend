package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import com.projectgl.backend.User.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "registered_library_account", indexes = {
        @Index(columnList = "creationtimestamp"),
        @Index(columnList = "updatetimestamp")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RegisteredLibraryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "apikey")
    private String apiKey;

    @Column(name = "refreshkey")
    private String refreshkey;

    @Column(name = "creationtimestamp", nullable = false, updatable = false)
    private LocalDateTime creationTimeStamp = LocalDateTime.now();

    @Column(name = "updatetimestamp", nullable = false)
    private LocalDateTime updateTimeStamp = LocalDateTime.now();

    @Column(name = "type")
    private String accountType;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
