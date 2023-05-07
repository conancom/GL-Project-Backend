package com.projectgl.backend.User;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user", indexes = {
        @Index(columnList = "username"),
        @Index(columnList = "email"),
        @Index(columnList = "updatetimestamp")
}, schema = "public")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registrationTimeStamp = LocalDateTime.now();
        this.updateTimeStamp = LocalDateTime.now();
        this.lastLoginTimeStamp = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegisteredLibraryAccount> registeredLibraryAccountList;

    @Column(name = "registrationtimestamp", nullable = false, updatable = false)
    private LocalDateTime registrationTimeStamp = LocalDateTime.now();

    @Column(name = "updatetimestamp", nullable = false)
    private LocalDateTime updateTimeStamp = LocalDateTime.now();

    @Column(name = "lastlogintimestamp", nullable = false)
    private LocalDateTime lastLoginTimeStamp = LocalDateTime.now();

    public void addRegisteredLibraryAccount(RegisteredLibraryAccount registeredLibraryAccount){
        this.registeredLibraryAccountList.add(registeredLibraryAccount);
        registeredLibraryAccount.setUser(this);
    }

}
