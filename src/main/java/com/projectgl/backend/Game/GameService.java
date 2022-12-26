package com.projectgl.backend.Game;

import com.projectgl.backend.Dto.GogGameDetailsResponse;
import com.projectgl.backend.Dto.SteamResponseGame;
import com.projectgl.backend.Response.IgdbGameResponse;

public interface GameService {

    void synchronizeGames();

    Game getGameInformationFromIgdb(String gameName);

    Game synchronizeGameFromSteam(SteamResponseGame steamResponseGame);

    Game synchronizeGameFromGog(GogGameDetailsResponse gogGameDetailsResponse);
}

