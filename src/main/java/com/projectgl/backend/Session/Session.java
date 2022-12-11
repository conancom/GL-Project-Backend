package com.projectgl.backend.Session;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session", schema = "public")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sessionid")
    private String sessionId;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "creationtimestamp")
    private LocalDateTime creationTimeStamp;

    @Column(name = "updatetimestamp")
    private LocalDateTime updateTimeStamp;

    @Column(name = "expirationtimestamp")
    private LocalDateTime expirationTimeStamp;

}
