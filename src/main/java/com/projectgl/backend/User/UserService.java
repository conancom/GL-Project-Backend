package com.projectgl.backend.User;

import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.RegisterResponse;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public boolean userExistsUsername(RegisterDto registerDto);

    public boolean userExistsEmail(RegisterDto registerDto);

    public RegisterResponse createUser(RegisterDto registerDto);

    public LoginResponse loginUser(LoginDto loginDto, HttpServletRequest request);

    public String createToken();
}
