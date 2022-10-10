package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Dto.GameDetail;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import com.projectgl.backend.Response.LibraryGamesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredLibraryAccountServiceImpl implements RegisteredLibraryAccountService{

    final private RegisteredLibraryAccountRepository registeredLibraryAccountRepository;

    @Autowired
    public RegisteredLibraryAccountServiceImpl(RegisteredLibraryAccountRepository registeredLibraryAccountRepository) {
        this.registeredLibraryAccountRepository = registeredLibraryAccountRepository;
    }

    public Optional<RegisteredLibraryAccount> findRegisteredLibraryAccountById(long registeredLibraryAccountId) {
        return registeredLibraryAccountRepository.findById(registeredLibraryAccountId);
    }

    public LibraryGamesResponse createLibraryAccountResponse(long userId, long libraryAccountId) {
        Optional<RegisteredLibraryAccount> registeredLibraryAccountOpt = findRegisteredLibraryAccountById(libraryAccountId);
        LibraryGamesResponse libraryGamesResponse;
        if (registeredLibraryAccountOpt.isEmpty()) {
            libraryGamesResponse = LibraryGamesResponse.builder().status(LibraryGamesResponse.Status.NO_ACCOUNT_FOUND).build();
            return libraryGamesResponse;
        }
        libraryGamesResponse = LibraryGamesResponse.builder().build();
        RegisteredLibraryAccount registeredLibraryAccount = registeredLibraryAccountOpt.get();
        if (!registeredLibraryAccount.getUser().getId().equals(userId)){
            libraryGamesResponse.setStatus(LibraryGamesResponse.Status.ACCOUNT_ID_MISSMATCH);
            return libraryGamesResponse;
        }
        List<PersonalGameInformation> personalGameInformations = registeredLibraryAccount.getPersonalGameInformationList();
        libraryGamesResponse.setGameDetails(new ArrayList<>());
        personalGameInformations.forEach(personalGameInformation -> {
            GameDetail gameDetail = GameDetail.builder()
                    .game_name(personalGameInformation.getGame().getName())
                    .personal_game_id(personalGameInformation.getId())
                    .game_id(personalGameInformation.getGame().getId())
                    .game_description(personalGameInformation.getGame().getInformation())
                    .picture_url(personalGameInformation.getGame().getProfileImg())
                    .banner_url(personalGameInformation.getGame().getBackgroundImg())
                    .libraryName(registeredLibraryAccount.getAccountType())
                    .library_id(registeredLibraryAccount.getId()).build();
            libraryGamesResponse.getGameDetails().add(gameDetail);
        });
        libraryGamesResponse.getGameDetails().sort(Comparator.comparing(GameDetail::getGame_name));
        libraryGamesResponse.setStatus(LibraryGamesResponse.Status.SESSION_KEY_OK);
        return libraryGamesResponse;
    }
}
