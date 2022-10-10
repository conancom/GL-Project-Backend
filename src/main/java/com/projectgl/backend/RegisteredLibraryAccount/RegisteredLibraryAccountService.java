package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Response.LibraryGamesResponse;

import java.util.Optional;

public interface RegisteredLibraryAccountService {

    public Optional<RegisteredLibraryAccount> findRegisteredLibraryAccountById(long registeredLibraryAccountId);

    public LibraryGamesResponse createLibraryAccountResponse(long userId, long reg);

}
