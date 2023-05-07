package com.projectgl.backend.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomSessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findSessionBySessionId(String Id);

}
