package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.GameDto;
import com.projectgl.backend.Game.GameService;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformationService;
import com.projectgl.backend.Response.GameResponse;
import com.projectgl.backend.Response.IgdbGameResponse;
import com.projectgl.backend.Session.SessionService;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class GameController {

    final public UserService userService;

    final public PersonalGameInformationService personalGameInformationService;

    final public GameService gameService;

    final public SessionService sessionService;

    @Autowired
    public GameController(UserService userService, PersonalGameInformationService personalGameInformationService, GameService gameService, SessionService sessionService) {
        this.userService = userService;
        this.personalGameInformationService = personalGameInformationService;
        this.gameService = gameService;
        this.sessionService = sessionService;
    }

    @PostMapping("api/v1/game-info")
    public ResponseEntity<GameResponse> getGameInformation(@RequestBody GameDto gameDto, HttpServletRequest request){

        if(!sessionService.validateSession(gameDto.getSession_id())){
            return ResponseEntity.ok(GameResponse.builder().status(GameResponse.Status.SESSION_EXPIRED).build());
        }
        long userId = sessionService.getUserId(gameDto.getSession_id());
        sessionService.refreshSession(gameDto.getSession_id());
        return ResponseEntity.ok(personalGameInformationService.createGameResponse(userId, gameDto.getGame_id()));
    }

}
