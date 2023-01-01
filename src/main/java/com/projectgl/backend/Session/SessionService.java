package com.projectgl.backend.Session;

import org.springframework.stereotype.Service;

@Service
public interface SessionService {

    public void createSession(String sessionId, Long userId);

    public boolean validateSession(String sessionId);

    public void refreshSession(String sessionId);

    public Long getUserId(String sessionId);

    public void destroySession(String sessionId);

}
