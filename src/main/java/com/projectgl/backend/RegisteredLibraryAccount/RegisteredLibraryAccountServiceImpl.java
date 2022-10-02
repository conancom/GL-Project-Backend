package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Dto.LibraryAccountDetails;
import com.projectgl.backend.Response.AllLibraryGamesResponse;
import com.projectgl.backend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class RegisteredLibraryAccountServiceImpl implements RegisteredLibraryAccountService{
    final RegisteredLibraryAccountRepository registeredLibraryAccountRepository;

    @Autowired
    public RegisteredLibraryAccountServiceImpl(RegisteredLibraryAccountRepository registeredLibraryAccountRepository) {
        this.registeredLibraryAccountRepository = registeredLibraryAccountRepository;
    }

    public ArrayList<RegisteredLibraryAccount> fingRegisteredLibraryAccountsByUser(User user) {
        return registeredLibraryAccountRepository.getRegisteredLibraryAccountsByUser(user);
    }

    public AllLibraryGamesResponse createAllLibraryGamesResponse(User user) {
        ArrayList<RegisteredLibraryAccount> registeredLibraryAccounts = fingRegisteredLibraryAccountsByUser(user);
        AllLibraryGamesResponse allLibraryGamesResponse = AllLibraryGamesResponse.builder().build();
        for (RegisteredLibraryAccount registeredLibraryAccount: registeredLibraryAccounts) {
            LibraryAccountDetails libraryAccountDetails = LibraryAccountDetails.builder().build();
            libraryAccountDetails.setLibraryName(registeredLibraryAccount.getAccountType());
            libraryAccountDetails.setLibrary_id(registeredLibraryAccount.getId());

        }

        return null;
    }


}
