package com.projectgl.backend.Session;

import org.springframework.stereotype.Service;

@Service
public interface SessionService {

    void createSession(String sessionId, Long userId);

    boolean validateSession(String sessionId);

    void refreshSession(String sessionId);

    Long getUserId(String sessionId);

    void destroySession(String sessionId);

}
