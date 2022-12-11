package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LibraryRegisterResponse;
import com.projectgl.backend.Response.LibraryResponse;

import java.util.Optional;

public interface RegisteredLibraryAccountService {

    public Optional<RegisteredLibraryAccount> findRegisteredLibraryAccountById(long registeredLibraryAccountId);

    public LibraryGamesResponse createLibraryAccountResponse(long userId, long reg);

    public LibraryRegisterResponse registerLibraryAccount(long userId, String libraryType, String libraryApiKey);

    public LibraryResponse getAllLibraryAccounts(long userId);

}
