package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.AllLibraryGamesDto;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccountService;
import com.projectgl.backend.Response.AllLibraryGamesResponse;
import com.projectgl.backend.User.User;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin
@RestController
public class LibraryController {

    final RegisteredLibraryAccountService registeredLibraryAccountService;

    final UserService userService;

    @Autowired
    public LibraryController(RegisteredLibraryAccountService registeredLibraryAccountService, UserService userService) {
        this.registeredLibraryAccountService = registeredLibraryAccountService;
        this.userService = userService;
    }


    @PostMapping("/api/v1/all-library-games")
    public AllLibraryGamesResponse getAllLibraryGames(@RequestBody AllLibraryGamesDto allLibraryGamesDto, HttpServletRequest request) {
        long userId = (long) request.getSession().getAttribute(allLibraryGamesDto.getSession_id());
        if (userId == 0) {
            return AllLibraryGamesResponse.builder().status(AllLibraryGamesResponse.Status.SESSION_EXPIRED).build();
        }
        Optional<User> user = userService.findUserbyId(userId);
        if (user.isEmpty()) {
            return AllLibraryGamesResponse.builder().status(AllLibraryGamesResponse.Status.USER_DOES_NOT_EXIST).build();
        }
        return registeredLibraryAccountService.createAllLibraryGamesResponse(user.get());
    }
}