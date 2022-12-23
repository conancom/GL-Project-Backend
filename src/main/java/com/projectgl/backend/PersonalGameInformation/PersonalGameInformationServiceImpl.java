package com.projectgl.backend.PersonalGameInformation;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import com.projectgl.backend.Response.GameResponse;
import com.projectgl.backend.Response.GameScreenshotResponse;
import com.projectgl.backend.Response.GameVideoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PersonalGameInformationServiceImpl implements PersonalGameInformationService {

    final PersonalGameInformationRepository personalGameInformationRepository;

    @Autowired
    public PersonalGameInformationServiceImpl(PersonalGameInformationRepository personalGameInformationRepository) {
        this.personalGameInformationRepository = personalGameInformationRepository;
    }

    public ArrayList<PersonalGameInformation> findPersonalGameInformationByRegisteredLibraryAccount(RegisteredLibraryAccount registeredLibraryAccount) {
        return personalGameInformationRepository.getPersonalGameInformationByRegisteredLibraryAccount(registeredLibraryAccount);
    }

    public GameResponse createGameResponse(long userId, long gameId) {

        Optional<PersonalGameInformation> personalGameInformationOpt = personalGameInformationRepository.findById(gameId);

        if (personalGameInformationOpt.isEmpty()) {
            return GameResponse.builder().status(GameResponse.Status.NO_ACCOUNT_FOUND).build();
        }

        PersonalGameInformation personalGameInformation = personalGameInformationOpt.get();
        if (!personalGameInformation.registeredLibraryAccount.getUser().getId().equals(userId)){
            return GameResponse.builder().status(GameResponse.Status.ACCOUNT_ID_MISSMATCH).build();
        }

        ArrayList<GameVideoResponse> gameVideoResponse = new ArrayList<>();

        personalGameInformation.getGame().getGameVideos().forEach( videos -> {
            gameVideoResponse.add(GameVideoResponse.builder().video_id(videos.getVideo_id()).name(videos.getName()).build());
        });

        ArrayList<GameScreenshotResponse> gameScreenshotResponse = new ArrayList<>();

        personalGameInformation.getGame().getGameScreenshots().forEach( screenshot -> {
            gameScreenshotResponse.add(GameScreenshotResponse.builder().screenshot_url(screenshot.getScreenshot_url()).build());
        });

        return GameResponse.builder()
                .game_name(personalGameInformation.getGame().getName())
                .personal_game_id(personalGameInformation.getId())
                .game_id(personalGameInformation.getGame().getId())
                .summary(personalGameInformation.getGame().getSummary())
                .storyline(personalGameInformation.getGame().getStoryline())
                .rating(personalGameInformation.getGame().getRating())
                .first_release_date(personalGameInformation.getGame().getFirst_release_date())
                .picture_url(personalGameInformation.getGame().getProfileImg())
                .banner_url(personalGameInformation.getGame().getBackgroundImg())
                .library_name(personalGameInformation.getRegisteredLibraryAccount().getAccountType())
                .library_id(personalGameInformation.getRegisteredLibraryAccount().getId())
                .videos(gameVideoResponse)
                .screenshots(gameScreenshotResponse)
                .status(GameResponse.Status.SESSION_KEY_OK).build();
    }

}
