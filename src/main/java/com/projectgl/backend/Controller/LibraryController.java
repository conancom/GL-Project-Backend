package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.AllLibraryGamesDto;
import com.projectgl.backend.Dto.LibraryGamesDto;
import com.projectgl.backend.Dto.LibraryRegisterDto;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccountService;
import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LibraryRegisterResponse;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;

@CrossOrigin
@RestController
public class LibraryController {

    final public RegisteredLibraryAccountService registeredLibraryAccountService;

    final public UserService userService;

    @Autowired
    public LibraryController(RegisteredLibraryAccountService registeredLibraryAccountService, UserService userService) {
        this.registeredLibraryAccountService = registeredLibraryAccountService;
        this.userService = userService;
    }

    @PostMapping("/api/v1/all-library-games/alphabetically")
    public LibraryGamesResponse getAllLibraryGames(@RequestBody AllLibraryGamesDto allLibraryGamesDto, HttpServletRequest request) {
        Object userId = request.getSession().getAttribute(allLibraryGamesDto.getSession_id());
        if (userId == null) {
            return LibraryGamesResponse.builder().status(LibraryGamesResponse.Status.SESSION_EXPIRED).build();
        }
        return userService.createAllLibraryAccountResponse((long) userId);
    }

    @PostMapping("/api/v1/library-games/alphabetically")
    public LibraryGamesResponse getLibraryGames(@RequestBody LibraryGamesDto libraryGamesDto, HttpServletRequest request) {
        Object userId = request.getSession(false).getAttribute(libraryGamesDto.getSession_id());
        if (userId == null) {
            return LibraryGamesResponse.builder().status(LibraryGamesResponse.Status.SESSION_EXPIRED).build();
        }
        return registeredLibraryAccountService.createLibraryAccountResponse((long) userId, libraryGamesDto.getLibrary_id());
    }

    @PostMapping("/api/v1/register-library")
    public ResponseEntity<?> registerLibrary(@RequestBody LibraryRegisterDto libraryRegisterDto, HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("JSESSIONID") != null) {
            Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
            response.addCookie(userCookie);
        } else {
            String sessionId = request.getSession(false).getId();
            Cookie userCookie = new Cookie("JSESSIONID", sessionId);
            response.addCookie(userCookie);
        }
        Object userId = request.getSession(false).getAttribute(libraryRegisterDto.getSession_id());
        System.out.println(libraryRegisterDto.getSession_id());
        if (userId == null) {
            return ResponseEntity.ok(LibraryRegisterResponse.builder().status(LibraryRegisterResponse.Status.SESSION_EXPIRED).build());
        }
        return ResponseEntity.ok(registeredLibraryAccountService.registerLibraryAccount((long) userId, libraryRegisterDto.getLibrary_type(), libraryRegisterDto.getLibrary_api_key()));
    }

}
