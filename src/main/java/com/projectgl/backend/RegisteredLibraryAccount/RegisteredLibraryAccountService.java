package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LibraryRegisterResponse;
import com.projectgl.backend.Response.LibraryResponse;

import java.util.Optional;

public interface RegisteredLibraryAccountService {

    Optional<RegisteredLibraryAccount> findRegisteredLibraryAccountById(long registeredLibraryAccountId);

    LibraryGamesResponse createLibraryAccountResponse(long userId, long reg);

    LibraryRegisterResponse registerLibraryAccount(long userId, String libraryType, String libraryApiKey);

    LibraryResponse getAllLibraryAccounts(long userId);

}
