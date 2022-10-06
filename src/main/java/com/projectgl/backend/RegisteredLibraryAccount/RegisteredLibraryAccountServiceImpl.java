package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Dto.GameDetail;
import com.projectgl.backend.Dto.LibraryAccountDetails;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformationService;
import com.projectgl.backend.Response.AllLibraryGamesResponse;
import com.projectgl.backend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class RegisteredLibraryAccountServiceImpl implements RegisteredLibraryAccountService{
    final RegisteredLibraryAccountRepository registeredLibraryAccountRepository;
    final PersonalGameInformationService personalGameInformationService;

    @Autowired
    public RegisteredLibraryAccountServiceImpl(RegisteredLibraryAccountRepository registeredLibraryAccountRepository, PersonalGameInformationService personalGameInformationService) {
        this.registeredLibraryAccountRepository = registeredLibraryAccountRepository;
        this.personalGameInformationService = personalGameInformationService;
    }

    public ArrayList<RegisteredLibraryAccount> fingRegisteredLibraryAccountsByUser(User user) {
        return registeredLibraryAccountRepository.getRegisteredLibraryAccountsByUser(user);
    }

    public AllLibraryGamesResponse createAllLibraryGamesResponse(User user) {
        ArrayList<RegisteredLibraryAccount> registeredLibraryAccounts = fingRegisteredLibraryAccountsByUser(user);
        AllLibraryGamesResponse allLibraryGamesResponse = AllLibraryGamesResponse.builder().build();
        allLibraryGamesResponse.setLibraries(new ArrayList<>());
        registeredLibraryAccounts.forEach(registeredLibraryAccount -> {
                    LibraryAccountDetails libraryAccountDetails = LibraryAccountDetails.builder().build();
                    libraryAccountDetails.setLibraryName(registeredLibraryAccount.getAccountType());
                    libraryAccountDetails.setLibrary_id(registeredLibraryAccount.getId());
                    libraryAccountDetails.setGameDetails(new ArrayList<>());
                    ArrayList<PersonalGameInformation> personalGameInformations = personalGameInformationService.findPersonalGameInformationByRegisteredLibraryAccount(registeredLibraryAccount);
                    personalGameInformations.forEach(personalGameInformation -> {
                        GameDetail gameDetail = GameDetail.builder().build();
                        gameDetail.setGame_name(personalGameInformation.getGame().getName());
                        gameDetail.setPersonal_game_id(personalGameInformation.getId());
                        gameDetail.setGame_id(personalGameInformation.getGame().getId());
                        gameDetail.setGame_description(personalGameInformation.getGame().getInformation());
                        gameDetail.setPicture_url(personalGameInformation.getGame().getProfileImg());
                        gameDetail.setBanner_url(personalGameInformation.getGame().getBackgroudImg());
                        libraryAccountDetails.getGameDetails().add(gameDetail);
                    });
                    allLibraryGamesResponse.getLibraries().add(libraryAccountDetails);
                });
        allLibraryGamesResponse.setStatus(AllLibraryGamesResponse.Status.SESSION_KEY_OK);
        return allLibraryGamesResponse;
    }


}
