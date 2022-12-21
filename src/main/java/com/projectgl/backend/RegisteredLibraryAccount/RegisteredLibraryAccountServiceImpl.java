package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Dto.GameDetail;
import com.projectgl.backend.Dto.LibraryDetail;
import com.projectgl.backend.Dto.SteamGames;
import com.projectgl.backend.Game.Game;
import com.projectgl.backend.Game.GameRepository;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformationRepository;
import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LibraryRegisterResponse;
import com.projectgl.backend.Response.LibraryResponse;
import com.projectgl.backend.User.User;
import com.projectgl.backend.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredLibraryAccountServiceImpl implements RegisteredLibraryAccountService{

    final private RegisteredLibraryAccountRepository registeredLibraryAccountRepository;
    final private UserRepository userRepository;
    final private GameRepository gameRepository;
    final private PersonalGameInformationRepository personalGameInformationRepository;

    @Autowired
    public RegisteredLibraryAccountServiceImpl(RegisteredLibraryAccountRepository registeredLibraryAccountRepository, UserRepository userRepository, GameRepository gameRepository, PersonalGameInformationRepository personalGameInformationRepository) {
        this.registeredLibraryAccountRepository = registeredLibraryAccountRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.personalGameInformationRepository = personalGameInformationRepository;
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
        libraryGamesResponse.setGames(new ArrayList<>());
        personalGameInformations.forEach(personalGameInformation -> {
            GameDetail gameDetail = GameDetail.builder()
                    .game_name(personalGameInformation.getGame().getName())
                    .personal_game_id(personalGameInformation.getId())
                    .game_id(personalGameInformation.getGame().getId())
                    .game_description(personalGameInformation.getGame().getInformation())
                    .picture_url(personalGameInformation.getGame().getProfileImg())
                    .banner_url(personalGameInformation.getGame().getBackgroundImg())
                    .library_name(registeredLibraryAccount.getAccountType())
                    .library_id(registeredLibraryAccount.getId()).build();
            libraryGamesResponse.getGames().add(gameDetail);
        });
        libraryGamesResponse.getGames().sort(Comparator.comparing(GameDetail::getGame_name));
        libraryGamesResponse.setStatus(LibraryGamesResponse.Status.SESSION_KEY_OK);
        return libraryGamesResponse;
    }

    private SteamGames getSteamGames(String steamId) {
        final String uri = String.format("http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=FF39BF9B9F79359FF343088F387F9206&steamid=%s&format=json&include_appinfo=true&include_played_free_games=true", steamId);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, SteamGames.class);
    }

    public LibraryRegisterResponse registerLibraryAccount(long userId, String libraryType, String libraryApiKey) {
        Optional<User> optUser = userRepository.findById(userId);

        if (optUser.isEmpty()){
            return LibraryRegisterResponse.builder()
                    .status(LibraryRegisterResponse.Status.SESSION_KEY_OK)
                    .library_key_status(LibraryRegisterResponse.Status.NO_ACCOUNT_FOUND)
                    .build();
        }
        User user = optUser.get();

        // Complete Link http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=FF39BF9B9F79359FF343088F387F9206&steamid=76561198058575875&format=json&include_appinfo=true
        // Image Link http://media.steampowered.com/steamcommunity/public/images/apps/218620/a6abc0d0c1e79c0b5b0f5c8ab81ce9076a542414.jpg

        RegisteredLibraryAccount registeredLibraryAccount;
        if(libraryType.equals("STEAM")){
            SteamGames steamResponse = getSteamGames(libraryApiKey);
            if(steamResponse.getResponse() == null){
                return LibraryRegisterResponse.builder().library_key_status(LibraryRegisterResponse.Status.STEAM_ACCOUNT_NOT_FOUND).build();
            }
            registeredLibraryAccount = RegisteredLibraryAccount.builder()
                    .user(user)
                    .accountType(libraryType)
                    .apiKey(libraryApiKey)
                    .creationTimeStamp(LocalDateTime.now())
                    .updateTimeStamp(LocalDateTime.now())
                    .personalGameInformationList(new ArrayList<PersonalGameInformation>())
                    .build();
            registeredLibraryAccountRepository.saveAndFlush(registeredLibraryAccount);
            steamResponse.getResponse().getGames().forEach(steamResponseGame -> {
                Optional<Game> optGame = gameRepository.findGameByName(steamResponseGame.getName());
                Game game;
                if(optGame.isEmpty()){
                    game = Game.builder()
                            .name(steamResponseGame.getName())
                            .title(steamResponseGame.getName())
                            .information("placeholder")
                            .backgroundImg("placeholder")
                            .creationTimeStamp(LocalDateTime.now())
                            .updateTimeStamp(LocalDateTime.now())
                            .profileImg(steamResponseGame.getImg_icon_url())
                            .build();
                    gameRepository.saveAndFlush(game);
                }else {
                    game = optGame.get();
                }
                PersonalGameInformation gameInformation = PersonalGameInformation.builder()
                        .registeredLibraryAccount(registeredLibraryAccount)
                        .creationTimeStamp(LocalDateTime.now())
                        .updateTimeStamp(LocalDateTime.now())
                        .game(game)
                        .build();
                personalGameInformationRepository.saveAndFlush(gameInformation);
                registeredLibraryAccount.getPersonalGameInformationList().add(gameInformation);
            });


            user.getRegisteredLibraryAccountList().add(registeredLibraryAccount);
            userRepository.saveAndFlush(user);
        }
        return LibraryRegisterResponse.builder()
                .status(LibraryRegisterResponse.Status.SESSION_KEY_OK)
                .library_key_status(LibraryRegisterResponse.Status.ADDED_SUCCESSFUL)
                .build();
    }

    public LibraryResponse getAllLibraryAccounts(long userId) {
        Optional<User> optUser = userRepository.findById(userId);

        if (optUser.isEmpty()){
            return LibraryResponse.builder()
                    .status(LibraryResponse.Status.SESSION_KEY_OK)
                    .build();
        }
        User user = optUser.get();
        LibraryResponse libraryResponse = LibraryResponse.builder()
                .status(LibraryResponse.Status.SESSION_KEY_OK)
                .libraries(new ArrayList<>())
                .build();
        user.getRegisteredLibraryAccountList().forEach(registeredLibraryAccount -> {
            LibraryDetail libraryDetail = LibraryDetail.builder()
                    .library_type(registeredLibraryAccount.getAccountType())
                    .library_id(registeredLibraryAccount.getId())
                    .build();
            libraryResponse.getLibraries().add(libraryDetail);
        });
        return libraryResponse;
    }
}
