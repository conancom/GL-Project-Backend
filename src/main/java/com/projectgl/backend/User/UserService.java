package com.projectgl.backend.User;

import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.RegisterResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {

    boolean userExistsUsername(RegisterDto registerDto);

    boolean userExistsEmail(RegisterDto registerDto);

    RegisterResponse createUser(RegisterDto registerDto);

    LoginResponse loginUser(LoginDto loginDto, HttpServletRequest request);

    Optional<User> findUserbyId(long userId);

    LibraryGamesResponse createAllLibraryAccountResponse(Long userId);

}
