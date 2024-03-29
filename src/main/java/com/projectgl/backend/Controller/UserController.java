package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.LogoutDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.LogoutResponse;
import com.projectgl.backend.Response.RegisterResponse;
import com.projectgl.backend.Session.SessionService;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
public class UserController {

    final public UserService userService;

    final public SessionService sessionService;

    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<RegisterResponse> registerNewUser(@Valid @RequestBody RegisterDto registerDto, HttpServletRequest request) {
        if (userService.userExistsUsername(registerDto)) {
            return ResponseEntity.ok(RegisterResponse.builder().username(registerDto.getUsername()).status(RegisterResponse.Status.DUPLICATE_USERNAME).build());
        }
        if (userService.userExistsEmail(registerDto)) {
            return ResponseEntity.ok(RegisterResponse.builder().username(registerDto.getUsername()).status(RegisterResponse.Status.DUPLICATE_EMAIL).build());
        }
        return ResponseEntity.ok(userService.createUser(registerDto));
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        return ResponseEntity.ok(userService.loginUser(loginDto, request));
    }

    @PostMapping("/api/v1/logout")
    public ResponseEntity<LogoutResponse> logoutUser(@Valid @RequestBody LogoutDto logoutDto){
        if(!sessionService.validateSession(logoutDto.getSession_id())){
            return ResponseEntity.ok(LogoutResponse.builder().status("SESSION_EXPIRED").build());
        }
        sessionService.destroySession(logoutDto.getSession_id());
        return ResponseEntity.ok(LogoutResponse.builder().status("SUCCESS").build());
    }
}
