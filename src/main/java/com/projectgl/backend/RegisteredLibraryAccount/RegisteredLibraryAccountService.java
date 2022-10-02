package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Response.AllLibraryGamesResponse;
import com.projectgl.backend.User.User;

import java.util.ArrayList;

public interface RegisteredLibraryAccountService {
    ArrayList<RegisteredLibraryAccount> fingRegisteredLibraryAccountsByUser(User user);

    AllLibraryGamesResponse createAllLibraryGamesResponse(User user);

}
