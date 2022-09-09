package com.projectgl.backend.User;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany
    @JoinColumn(name = "userid")
    private List<RegisteredLibraryAccount> registeredLibraryAccountList;

    private LocalDateTime registrationTimeStamp;

    private LocalDateTime updateTimeStamp;

    private LocalDateTime lastLoginTimeStamp;

    public void addRegisteredLibraryAccount(RegisteredLibraryAccount registeredLibraryAccount){
        this.registeredLibraryAccountList.add(registeredLibraryAccount);
        registeredLibraryAccount.setUser(this);
    }

}
