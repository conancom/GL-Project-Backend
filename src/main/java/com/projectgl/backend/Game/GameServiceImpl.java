package com.projectgl.backend.Game;

import com.projectgl.backend.Dto.GogGameDetailsResponse;
import com.projectgl.backend.Dto.SteamResponseGame;
import com.projectgl.backend.Response.IgdbAuthResponse;
import com.projectgl.backend.Response.IgdbGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    static String ClientID = "527btjgrr29t3u6dj011v4e2g7ue1l";

    static String ClientSecret = "woeifs1uesyy2izr5kngec526kx9rd";

    final private GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void synchronizeGames() {

    }

    public Game fetchGame(String gameName) {
        return getGameInformationFromIgdb(gameName);
    }

    public Game synchronizeGameFromSteam(SteamResponseGame steamResponseGame) {
        String processedName = steamResponseGame.getName();
        String searchName = processedName.replaceAll("[-+.^:,®™]", "").replaceAll("\\(.*\\)", "").replaceAll("\\s+$", "").toLowerCase().replaceAll("\\s+", " ");
        Optional<Game> optGame = gameRepository.findGameBySearchName(searchName);
        Game game;
        if(optGame.isEmpty()){
            game = getGameInformationFromIgdb(searchName);
            if (game != null) {
                gameRepository.saveAndFlush(game);
            }
        }else {
            game = optGame.get();
        }
        return game;
    }

    public Game synchronizeGameFromGog(GogGameDetailsResponse gogGameDetailsResponse) {
        String processedName = gogGameDetailsResponse.getTitle(); //TODO: Find a better way to clean string
        gogGameDetailsResponse.setTitle(processedName.replaceAll("[-+.^:,®™]","").replaceAll("\\(.*\\)", "").replaceAll("\\s+$", "").toLowerCase().replaceAll("\\s+", " ")); //Remove Last Space
        Optional<Game> optGame = gameRepository.findGameByName(gogGameDetailsResponse.getTitle());
        Game game;
        if(optGame.isEmpty()){
            game = getGameInformationFromIgdb(gogGameDetailsResponse.getTitle());
            if (game != null) {
                gameRepository.save(game);
            }
        }else {
            game = optGame.get();
        }
        return game;
    }

    public Game getGameInformationFromIgdb(String gameName){
        final String authuri = String.format("https://id.twitch.tv/oauth2/token?client_id=%s&client_secret=%s&grant_type=client_credentials", ClientID, ClientSecret);
        RestTemplate restTemplateAuth = new RestTemplate();

        IgdbAuthResponse igdbAuthResponse = restTemplateAuth.postForObject(authuri, "",IgdbAuthResponse.class);

        final String findGameuri = "https://api.igdb.com/v4/games";
        RestTemplate restTemplateGame = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(igdbAuthResponse.getAccess_token());
        headers.set("Client-ID", ClientID);
        String body = String.format("search \"%s\"; fields name, id, cover, storyline, summary, first_release_date, artworks, rating, videos, screenshots;", gameName)   ;
        HttpEntity<String> requestEntityGame = new HttpEntity<>(body, headers);
        ResponseEntity<ArrayList> result = restTemplateGame.postForEntity(findGameuri, requestEntityGame, ArrayList.class);
        System.out.println("Init: " + gameName);

        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Optional foundGameMapPartialOpt = result.getBody().stream().filter(foundGame ->
            ((Map<String, Object>)foundGame).get("name") != null && ((Map<String, Object>)foundGame).get("name").toString().replaceAll("[-+.^:,®™()]","").toLowerCase().contains(gameName)).findAny();

        Optional foundGameMapOpt = result.getBody().stream().filter(foundGame ->
                ((Map<String, Object>)foundGame).get("name") != null && ((Map<String, Object>)foundGame).get("name").toString().replaceAll("[-+.^:,®™()]","").toLowerCase().equals(gameName)).findFirst();

        if (foundGameMapPartialOpt.isEmpty()){
            return null;
        }

        Map<String, Object> foundGameMap;

        if (foundGameMapOpt.isPresent()){
            foundGameMap = (Map<String, Object>) foundGameMapOpt.get();
        }else {
            foundGameMap = (Map<String, Object>) foundGameMapPartialOpt.get();
        }
        System.out.println("Found: " +  foundGameMap.get("name"));

        IgdbGameResponse foundGame = IgdbGameResponse.builder()
                .id((Integer) foundGameMap.get("id"))
                .cover((foundGameMap.get("cover")) == null ? 0 : (Integer) foundGameMap.get("cover"))
                .name((String) foundGameMap.get("name"))
                .artworks((ArrayList<Integer>) foundGameMap.get("artworks"))
                .summary((String) foundGameMap.get("summary"))
                .rating((foundGameMap.get("rating")) == null ? 0.0 : ((double) foundGameMap.get("rating")))
                .screenshots((foundGameMap.get("screenshots")) == null ? new ArrayList<>() :(ArrayList<Integer>) foundGameMap.get("screenshots"))
                .first_release_date((foundGameMap.get("first_release_date")) == null ? 0 :(Integer) foundGameMap.get("first_release_date"))
                .videos((foundGameMap.get("videos")) == null ? new ArrayList<>() :(ArrayList<Integer>) foundGameMap.get("videos"))
                .storyline((String) foundGameMap.get("storyline"))
                .build();

        final String findCoveruri = "https://api.igdb.com/v4/covers";
        String coverBody = String.format("fields image_id; where game = %s;", foundGame.getId())   ;
        HttpEntity<String> requestEntityCover = new HttpEntity<>(coverBody, headers);
        ResponseEntity<ArrayList> resultCover = restTemplateGame.postForEntity(findCoveruri, requestEntityCover, ArrayList.class);

        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String imageLink;
                Optional foundCoverMapOpt = resultCover.getBody().stream().filter(foundCover ->
            ((Map<String, Object>)foundCover).get("image_id") != null).findFirst();
        if (!foundCoverMapOpt.isEmpty()){
            Map<String, Object> foundCoverMap = (Map<String, Object>) foundCoverMapOpt.get();
            imageLink = String.format("//images.igdb.com/igdb/image/upload/t_1080p/%s.jpg", foundCoverMap.get("image_id"));
            System.out.println(imageLink);
            
        }else{
            imageLink = "Not Available";
        }

        //TODO: Add Artwork

        //Screenshots
        final String findScreenshoturi = "https://api.igdb.com/v4/screenshots";
        String screenshotBody = String.format("fields image_id; where game = %s;", foundGame.getId())   ;
        HttpEntity<String> requestEntityScreenShot = new HttpEntity<>(screenshotBody, headers);
        ResponseEntity<ArrayList> resultScreenShot = restTemplateGame.postForEntity(findScreenshoturi, requestEntityScreenShot, ArrayList.class);

        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        ArrayList<GameScreenshot> screentshots = new ArrayList<>();
        if (!resultScreenShot.getBody().isEmpty()){
            resultScreenShot.getBody().forEach( screentshot -> {
                String rawImageId = (String) ((Map<String, Object>) screentshot).get("image_id");
                String screenShot = String.format("//images.igdb.com/igdb/image/upload/t_1080p/%s.jpg", rawImageId);
                screentshots.add(GameScreenshot.builder()
                        .screenshot_url(screenShot)
                        .creationTimeStamp(LocalDateTime.now())
                        .updateTimeStamp(LocalDateTime.now())
                        .build());
            });
        }

        //Debug
        screentshots.forEach( screenshot -> {
            System.out.println(screenshot);
        });

        //Videos
        final String findVideouri = "https://api.igdb.com/v4/game_videos/";
        String videoBody = String.format("fields name, video_id; where game = %s;", foundGame.getId())   ;
        HttpEntity<String> requestEntityVideo = new HttpEntity<>(videoBody, headers);
        ResponseEntity<ArrayList> resultVideo = restTemplateGame.postForEntity(findVideouri, requestEntityVideo, ArrayList.class);

        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        ArrayList<GameVideo> videos = new ArrayList<>();
        if (!resultVideo.getBody().isEmpty()){
            resultVideo.getBody().forEach( screentshot -> {
                videos.add(GameVideo.builder()
                        .name(  (((Map<String, Object>)screentshot).get("name")) != null
                                ? ((String) ((Map<String, Object>)screentshot).get("name"))
                                : "not Available" )
                        .video_id((String) ((Map<String, Object>)screentshot).get("video_id"))
                        .creationTimeStamp(LocalDateTime.now())
                        .updateTimeStamp(LocalDateTime.now())
                        .build());
            });
        }

        //Debug
        videos.forEach( video -> {
            System.out.println(video.getVideo_id());
            System.out.println(video.getName());
        });

        Game game = Game.builder()
                .id((long) foundGame.getId())
                .title(foundGame.getName())
                .name(foundGame.getName())
                .backgroundImg(imageLink)
                .creationTimeStamp(LocalDateTime.now())
                .first_release_date(foundGame.getFirst_release_date())
                .profileImg(imageLink)
                .storyline(foundGame.getStoryline())
                .rating(foundGame.getRating())
                .summary(foundGame.getSummary())
                .personalGameInformationList(new ArrayList<>())
                .updateTimeStamp(LocalDateTime.now())
                .gameVideos(videos)
                .gameScreenshots(screentshots)
                .searchName(gameName)
                .build();
        videos.forEach( video -> {
            video.setGame(game);
        });
        screentshots.forEach( screentshot -> {
            screentshot.setGame(game);
        });
        return game;
    }
}
