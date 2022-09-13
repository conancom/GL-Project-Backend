package com.projectgl.backend.User;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public User(String username, String email, String password, String salt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.registrationTimeStamp = LocalDateTime.now();
        this.updateTimeStamp = LocalDateTime.now();
        this.lastLoginTimeStamp = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String salt;

    @OneToMany
    @JoinColumn(name = "userid")
    private List<RegisteredLibraryAccount> registeredLibraryAccountList;

    @Column(name = "registrationtimestamp")
    private LocalDateTime registrationTimeStamp;

    @Column(name = "updatetimestamp")
    private LocalDateTime updateTimeStamp;

    @Column(name = "lastlogintimestamp")
    private LocalDateTime lastLoginTimeStamp;

    public void addRegisteredLibraryAccount(RegisteredLibraryAccount registeredLibraryAccount){
        this.registeredLibraryAccountList.add(registeredLibraryAccount);
        registeredLibraryAccount.setUser(this);
    }

}
