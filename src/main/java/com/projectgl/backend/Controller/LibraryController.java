package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.AllLibraryDto;
import com.projectgl.backend.Dto.AllLibraryGamesDto;
import com.projectgl.backend.Dto.LibraryGamesDto;
import com.projectgl.backend.Dto.LibraryRegisterDto;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccountService;
import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LibraryRegisterResponse;
import com.projectgl.backend.Response.LibraryResponse;
import com.projectgl.backend.Session.SessionService;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
public class LibraryController {

    final public RegisteredLibraryAccountService registeredLibraryAccountService;

    final public UserService userService;

    final public SessionService sessionService;

    @Autowired
    public LibraryController(RegisteredLibraryAccountService registeredLibraryAccountService, UserService userService, SessionService sessionService) {
        this.registeredLibraryAccountService = registeredLibraryAccountService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/api/v1/all-library-games/alphabetically")
    public ResponseEntity<LibraryGamesResponse> getAllLibraryGames(@RequestBody AllLibraryGamesDto allLibraryGamesDto) {
        if(!sessionService.validateSession(allLibraryGamesDto.getSession_id())){
            return ResponseEntity.ok(LibraryGamesResponse.builder().status(LibraryGamesResponse.Status.SESSION_EXPIRED).build());
        }
        long userId = sessionService.getUserId(allLibraryGamesDto.getSession_id());
        sessionService.refreshSession(allLibraryGamesDto.getSession_id());
        return ResponseEntity.ok(userService.createAllLibraryAccountResponse(userId));
    }

    @PostMapping("/api/v1/library-games/alphabetically")
    public ResponseEntity<LibraryGamesResponse> getLibraryGames(@RequestBody LibraryGamesDto libraryGamesDto) {
        if(!sessionService.validateSession(libraryGamesDto.getSession_id())){
            return ResponseEntity.ok(LibraryGamesResponse.builder().status(LibraryGamesResponse.Status.SESSION_EXPIRED).build());
        }
        long userId = sessionService.getUserId(libraryGamesDto.getSession_id());
        sessionService.refreshSession(libraryGamesDto.getSession_id());
        return ResponseEntity.ok(registeredLibraryAccountService.createLibraryAccountResponse(userId, libraryGamesDto.getLibrary_id()));
    }

    @PostMapping("/api/v1/register-library")
    public ResponseEntity<LibraryRegisterResponse> registerLibrary(@RequestBody LibraryRegisterDto libraryRegisterDto, HttpServletRequest request, HttpServletResponse response) {
        if(!sessionService.validateSession(libraryRegisterDto.getSession_id())){
            return ResponseEntity.ok(LibraryRegisterResponse.builder().status(LibraryRegisterResponse.Status.SESSION_EXPIRED).build());
        }
        long userId = sessionService.getUserId(libraryRegisterDto.getSession_id());
        sessionService.refreshSession(libraryRegisterDto.getSession_id());
        return ResponseEntity.ok(registeredLibraryAccountService.registerLibraryAccount(userId, libraryRegisterDto.getLibrary_type(), libraryRegisterDto.getLibrary_api_key()));
    }

    @PostMapping("/api/v1/all-libraries")
    public ResponseEntity<LibraryResponse> getLibraries(@RequestBody AllLibraryDto allLibraryDto, HttpServletRequest request, HttpServletResponse response) {
        if(!sessionService.validateSession(allLibraryDto.getSession_id())){
            return ResponseEntity.ok(LibraryResponse.builder().status(LibraryResponse.Status.SESSION_EXPIRED).build());
        }
        long userId = sessionService.getUserId(allLibraryDto.getSession_id());
        sessionService.refreshSession(allLibraryDto.getSession_id());
        return ResponseEntity.ok(registeredLibraryAccountService.getAllLibraryAccounts(userId));
    }

}
