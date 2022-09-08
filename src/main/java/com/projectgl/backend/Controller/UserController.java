package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @PostMapping("/api/v1/register")
    public RegisterResponse registerNewUser(@RequestBody RegisterDto registerDto, HttpServletRequest request){
        return RegisterResponse.builder().build();
    }
}
