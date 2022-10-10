package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.GameDto;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformationService;
import com.projectgl.backend.Response.GameResponse;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public GameController(UserService userService, PersonalGameInformationService personalGameInformationService) {
        this.userService = userService;
        this.personalGameInformationService = personalGameInformationService;
    }

    @PostMapping("api/v1/game-info")
    public GameResponse getGameInformation(@RequestBody GameDto gameDto, HttpServletRequest request){
        Object userId = request.getSession().getAttribute(gameDto.getSession_id());
        if (userId == null) {
            return GameResponse.builder().status(GameResponse.Status.SESSION_EXPIRED).build();
        }
        return personalGameInformationService.createGameResponse((long) userId, gameDto.getGame_id());
    }

}
