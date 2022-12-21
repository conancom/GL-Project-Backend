package com.projectgl.backend.Game;

import com.projectgl.backend.Response.IgdbGameResponse;

public interface GameService {

    void synchronizeGames();

    Game fetchGame(String gameName);
}

