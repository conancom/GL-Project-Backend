package com.projectgl.backend.User;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;

public interface UserService {
    public boolean userExists(RegisterDto registerDto);
    public RegisterResponse createUser(RegisterDto registerDto);
}
