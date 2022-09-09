package com.projectgl.backend.User;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;

public interface UserService {
    public boolean userExistsUsername(RegisterDto registerDto);
    public boolean userExistsEmail(RegisterDto registerDto);
    public RegisterResponse createUser(RegisterDto registerDto);
}
