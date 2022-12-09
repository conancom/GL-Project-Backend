package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.RegisterResponse;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin
@RestController
public class UserController {

    final public UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/register")
    public RegisterResponse registerNewUser(@Valid @RequestBody RegisterDto registerDto, HttpServletRequest request) {
        if (userService.userExistsUsername(registerDto)) {
            return RegisterResponse.builder().username(registerDto.getUsername()).status(RegisterResponse.Status.DUPLICATE_USERNAME).build();
        }
        if (userService.userExistsEmail(registerDto)) {
            return RegisterResponse.builder().username(registerDto.getUsername()).status(RegisterResponse.Status.DUPLICATE_EMAIL).build();
        }
        return userService.createUser(registerDto);
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("JSESSIONID") != null) {
            Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
            response.addCookie(userCookie);
        } else {
            String sessionId = request.getSession().getId();
            Cookie userCookie = new Cookie("JSESSIONID", sessionId);
            response.addCookie(userCookie);
        }
        return ResponseEntity.ok(userService.loginUser(loginDto, request));
    }
}
