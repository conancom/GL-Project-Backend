package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.RegisterResponse;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    final public UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/register")
    public RegisterResponse registerNewUser(@RequestBody RegisterDto registerDto, HttpServletRequest request) {
        if (userService.userExistsUsername(registerDto)) {
            return RegisterResponse.builder().username(registerDto.getUsername()).status(RegisterResponse.Status.DUPLICATE_USERNAME).build();
        }
        if (userService.userExistsEmail(registerDto)) {
            return RegisterResponse.builder().username(registerDto.getUsername()).status(RegisterResponse.Status.DUPLICATE_EMAIL).build();
        }
        return userService.createUser(registerDto);
    }

    @GetMapping("/api/v1/login")
    public LoginResponse loginUser(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        LoginResponse loginResponse = userService.loginUser(loginDto);
        if (loginResponse.getStatus().equals(LoginResponse.Status.SUCCESS)) {
            loginResponse.setSession_id(userService.createToken());
            request.getSession().setAttribute(loginResponse.getSession_id(), "ID HERE");
        }
        return loginResponse;
    }
}
