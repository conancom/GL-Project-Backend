package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;
import com.projectgl.backend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    final public UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/register")
    public RegisterResponse registerNewUser(@RequestBody RegisterDto registerDto, HttpServletRequest request){
        if (userService.userExists(registerDto)){
            return RegisterResponse.builder().build();
        }else {
            return userService.createUser(registerDto);
        }
    }
}
