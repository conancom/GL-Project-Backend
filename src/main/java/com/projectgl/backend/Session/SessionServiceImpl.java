package com.projectgl.backend.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService{

    static int SESSION_DURATION_MAX_HOURS = 3;
    static int SESSION_REFRESH_TIME_HOURS = 3;

    final public CustomSessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(CustomSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void createSession(String sessionId, Long userId) {
        Session session = Session.builder()
                .sessionId(sessionId)
                .userId(userId)
                .creationTimeStamp(LocalDateTime.now())
                .updateTimeStamp(LocalDateTime.now())
                .expirationTimeStamp(LocalDateTime.now().plusMinutes(SESSION_DURATION_MAX_HOURS))
                .build();
        sessionRepository.save(session);
    }

    public boolean validateSession(String sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findSessionBySessionId(sessionId);
        if(sessionOpt.isEmpty()){
            return false;
        }
        if(LocalDateTime.now().isAfter(sessionOpt.get().getExpirationTimeStamp())){
            return false;
        }
        return true;
    }

    public void refreshSession(String sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findSessionBySessionId(sessionId);
        if(sessionOpt.isEmpty()){
            return;
        }
        Session session = sessionOpt.get();
        session.setExpirationTimeStamp(LocalDateTime.now().plusMinutes(SESSION_REFRESH_TIME_HOURS));
        session.setUpdateTimeStamp(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public Long getUserId(String sessionId) {
        return sessionRepository.findSessionBySessionId(sessionId).get().getUserId();
    }

    public void destroySession(String sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findSessionBySessionId(sessionId);
        if(sessionOpt.isEmpty()){
            return;
        }
        Session session = sessionOpt.get();
        session.setExpirationTimeStamp(LocalDateTime.now());
        session.setUpdateTimeStamp(LocalDateTime.now());
        sessionRepository.save(session);
    }


}
