package com.projectgl.backend.Game;

import com.projectgl.backend.Response.IgdbGameResponse;

public interface GameService {

    void synchronizeGames();

    String fetchGame(String gameName);
}

