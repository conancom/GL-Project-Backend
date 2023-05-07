package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.Dto.*;
import com.projectgl.backend.Game.Game;
import com.projectgl.backend.Game.GameRepository;
import com.projectgl.backend.Game.GameService;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformationRepository;
import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LibraryRegisterResponse;
import com.projectgl.backend.Response.LibraryResponse;
import com.projectgl.backend.User.User;
import com.projectgl.backend.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    final private GameService gameService;

    static String gogRedirectUriNewLogin = "https://embed.gog.com/on_login_success?origin=client";

    @Autowired
    public RegisteredLibraryAccountServiceImpl(RegisteredLibraryAccountRepository registeredLibraryAccountRepository, UserRepository userRepository, GameRepository gameRepository, PersonalGameInformationRepository personalGameInformationRepository, GameService gameService) {
        this.registeredLibraryAccountRepository = registeredLibraryAccountRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.personalGameInformationRepository = personalGameInformationRepository;
        this.gameService = gameService;
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
                    .summary(personalGameInformation.getGame().getSummary())
                    .storyline(personalGameInformation.getGame().getStoryline())
                    .rating(personalGameInformation.getGame().getRating())
                    .first_release_date(personalGameInformation.getGame().getFirst_release_date())
                    .picture_url(personalGameInformation.getGame().getProfileImg())
                    .banner_url(personalGameInformation.getGame().getBackgroundImg())
                    .library_name(registeredLibraryAccount.getAccountType())
                    .library_id(registeredLibraryAccount.getId())
                    .total_play_time(personalGameInformation.getTotaltimeplayed())
                    .build();
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

    private GogTokenResponse newLoginGogToken(String code){

        String encodedRedirectUri = URLEncoder.encode(gogRedirectUriNewLogin, StandardCharsets.UTF_8);

        final String uri = String.format("http://auth.gog.com/token?client_id=46899977096215655&client_secret=9d85c43b1482497dbbce61f6e4aa173a433796eeae2ca8c5f6129f2dc4de46d9&grant_type=authorization_code&code=%s&redirect_uri=%s", code, encodedRedirectUri);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, GogTokenResponse.class);
    }

    private GogAllGamesResponse getGogGames(String accessToken){
        final String uri = "embed.gog.com/user/data/games";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntityGame = new HttpEntity<>(headers);
        ResponseEntity<GogAllGamesResponse> result = restTemplate.exchange(uri, HttpMethod.GET ,requestEntityGame, GogAllGamesResponse.class);
        return result.getBody();
    }

    private GogGameDetailsResponse getGogGameDetails(int gameId, String accessToken){
        final String uri = String.format("embed.gog.com/account/gameDetails/%s.json", gameId);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntityGame = new HttpEntity<>(headers);
        ResponseEntity<GogGameDetailsResponse> result = restTemplate.exchange(uri, HttpMethod.GET ,requestEntityGame, GogGameDetailsResponse.class);
        return result.getBody();
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
                Game game = gameService.synchronizeGameFromSteam(steamResponseGame);
                if (game != null) { //TODO: Find more Edge Cases to Fix
                    PersonalGameInformation gameInformation = PersonalGameInformation.builder()
                            .registeredLibraryAccount(registeredLibraryAccount)
                            .creationTimeStamp(LocalDateTime.now())
                            .updateTimeStamp(LocalDateTime.now())
                            .game(game)
                            .totaltimeplayed(steamResponseGame.getPlaytime_forever())
                            .build();
                    game.getPersonalGameInformationList().add(gameInformation);
                    personalGameInformationRepository.saveAndFlush(gameInformation);
                    registeredLibraryAccount.getPersonalGameInformationList().add(gameInformation);
                }
            });
            user.getRegisteredLibraryAccountList().add(registeredLibraryAccount);

            userRepository.saveAndFlush(user);
        }else if(libraryType.equals("GOG")){
            GogTokenResponse gogTokenResponse = newLoginGogToken(libraryApiKey);
            if(gogTokenResponse == null){
                return LibraryRegisterResponse.builder().library_key_status(LibraryRegisterResponse.Status.GOG_ACCOUNT_NOT_FOUND).build();
            }
            GogAllGamesResponse gogAllGamesResponse = getGogGames(gogTokenResponse.getAccess_token());
            registeredLibraryAccount = RegisteredLibraryAccount.builder()
                    .user(user)
                    .accountType(libraryType)
                    .apiKey(gogTokenResponse.getAccess_token())
                    .refreshkey(gogTokenResponse.getRefresh_token())
                    .creationTimeStamp(LocalDateTime.now())
                    .updateTimeStamp(LocalDateTime.now())
                    .personalGameInformationList(new ArrayList<PersonalGameInformation>())
                    .build();
            registeredLibraryAccountRepository.saveAndFlush(registeredLibraryAccount);

            gogAllGamesResponse.getOwned().forEach( gameId -> {
                GogGameDetailsResponse gogGameDetailsResponse = getGogGameDetails(gameId, gogTokenResponse.getAccess_token());
                Game game = gameService.synchronizeGameFromGog(gogGameDetailsResponse);
                if (game != null) { //TODO: Find more Edge Cases to Fix
                    PersonalGameInformation gameInformation = PersonalGameInformation.builder()
                            .registeredLibraryAccount(registeredLibraryAccount)
                            .creationTimeStamp(LocalDateTime.now())
                            .updateTimeStamp(LocalDateTime.now())
                            .game(game)
                            .totaltimeplayed(0)
                            .build();
                    game.getPersonalGameInformationList().add(gameInformation);
                    personalGameInformationRepository.saveAndFlush(gameInformation);
                    registeredLibraryAccount.getPersonalGameInformationList().add(gameInformation);
                }
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
