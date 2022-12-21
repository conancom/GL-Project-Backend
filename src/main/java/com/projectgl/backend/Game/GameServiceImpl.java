package com.projectgl.backend.Game;

import com.projectgl.backend.Response.IgdbAuthResponse;
import com.projectgl.backend.Response.IgdbGameResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GameServiceImpl implements GameService {

    static String ClientID = "527btjgrr29t3u6dj011v4e2g7ue1l";

    static String ClientSecret = "woeifs1uesyy2izr5kngec526kx9rd";

    public void synchronizeGames() {

    }

    public String fetchGame(String gameName) {
        return getGameInformationFromIgdb(gameName);
    }

    private String getGameInformationFromIgdb(String gameName){
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

        Optional foundGameMapOpt = result.getBody().stream().filter(foundGame ->
            ((Map<String, Object>)foundGame).get("name") != null && ((Map<String, Object>)foundGame).get("name").equals(gameName)).findFirst();

        if (foundGameMapOpt.isEmpty()){
            return null;
        }
        Map<String, Object> foundGameMap = (Map<String, Object>) foundGameMapOpt.get();

        IgdbGameResponse foundGame = IgdbGameResponse.builder()
                .id((Integer) foundGameMap.get("id"))
                .cover((Integer) foundGameMap.get("cover"))
                .name((String) foundGameMap.get("name"))
                .artworks((ArrayList<Integer>) foundGameMap.get("artworks"))
                .summary((String) foundGameMap.get("summary"))
                .rating((Double) foundGameMap.get("rating"))
                .screenshots((ArrayList<Integer>) foundGameMap.get("screenshots"))
                .first_release_date((Integer) foundGameMap.get("first_release_date"))
                .videos((ArrayList<Integer>) foundGameMap.get("videos"))
                .storyline((String) foundGameMap.get("storyline"))
                .build();

        final String findCoveruri = "https://api.igdb.com/v4/covers";
        String coverBody = String.format("fields image_id; where game = %s;", foundGame.getId())   ;
        HttpEntity<String> requestEntityCover = new HttpEntity<>(coverBody, headers);
        ResponseEntity<ArrayList> resultCover = restTemplateGame.postForEntity(findCoveruri, requestEntityCover, ArrayList.class);

        Optional foundCoverMapOpt = resultCover.getBody().stream().filter(foundCover ->
            ((Map<String, Object>)foundCover).get("image_id") != null).findFirst();

        if (foundCoverMapOpt.isEmpty()){
            return null;
        }
        Map<String, Object> foundCoverMap = (Map<String, Object>) foundCoverMapOpt.get();

        String imageLink = String.format("//images.igdb.com/igdb/image/upload/t_1080p/%s.jpg", foundCoverMap.get("image_id"));

        return imageLink;




    }
}
