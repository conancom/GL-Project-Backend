package com.projectgl.backend.User;

import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.RegisterResponse;
import lombok.extern.java.Log;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    HttpServletRequest request;

    RegisterDto registerDto;

    LoginDto loginEmailDto;

    LoginDto loginUsernameDto;

    User user;


    @BeforeEach
    public void setUp() {
        registerDto = RegisterDto.builder().username("testUsername").email("testEmail@email.com").password("testPassword123").build();
        loginEmailDto = LoginDto.builder().username_email("test@email.com").password("password123").build();
        loginUsernameDto = LoginDto.builder().username_email("testUsername").password("password123").build();
        user = User.builder().username("testUsername").email("test@email.com").password("password123").build();
    }

    private void expectUsernameIsInDatabase() {
        Mockito.doReturn(true).when(userRepository).existsByUsername(registerDto.getUsername());
    }

    @Test
    public void userNameExistsShouldReturnTrueWhenUserNameIsInDatabase() {
        expectUsernameIsInDatabase();
        boolean result = userService.userExistsUsername(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(true));
    }

    private void expectUsernameIsNotInDatabase() {
        Mockito.doReturn(false).when(userRepository).existsByUsername(registerDto.getUsername());
    }

    @Test
    public void userNameExistsShouldReturnFalseWhenUserNameIsNotInDatabase() {
        expectUsernameIsNotInDatabase();
        boolean result = userService.userExistsUsername(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(false));
    }

    private void expectEmailIsInDatabase() {
        Mockito.doReturn(true).when(userRepository).existsByEmail(registerDto.getEmail());
    }

    @Test
    public void emailExistsShouldReturnTrueWhenEmailIsInDatabase() {
        expectEmailIsInDatabase();
        boolean result = userService.userExistsEmail(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(true));
    }

    private void expectEmailIsNotInDatabase() {
        Mockito.doReturn(false).when(userRepository).existsByEmail(registerDto.getEmail());
    }

    @Test
    public void emailExistsShouldReturnFalseWhenEmailIsNotInDatabase() {
        expectEmailIsNotInDatabase();
        boolean result = userService.userExistsEmail(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(false));
    }

    private void expectSaveUser() {
        Mockito.lenient().when(userRepository.save(User.builder().username(registerDto.getUsername()).email(registerDto.getEmail()).password(registerDto.getPassword()).build())).thenReturn(null);
    }

    @Test
    public void createUserShouldCreateANewUser() {
        expectSaveUser();
        RegisterResponse result = userService.createUser(registerDto);
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(registerDto.getUsername()));
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(RegisterResponse.Status.SUCCESS));
    }

    public void expectEmailExists() {
        Mockito.when(userRepository.findByEmail(loginEmailDto.getUsername_email())).thenReturn(Optional.ofNullable(user));
    }

    @Test
    public void loginUserShouldLoginUserWhenEmailIsCorrect() {
        expectEmailExists();
        LoginResponse result = userService.loginUser(loginEmailDto, request);
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(user.getUsername()));
    }

    public void expectEmailDoesntExist() {
        Mockito.when(userRepository.findByEmail(loginEmailDto.getUsername_email())).thenReturn(Optional.empty());
    }

    @Test
    public void loginUserShouldNotLoginUserWhenEmailIsNotCorrect() {
        expectEmailDoesntExist();
        LoginResponse result = userService.loginUser(loginEmailDto, request);
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(user.getEmail()));
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LoginResponse.Status.INVALID_EMAIL));
    }

    public void expectUsernameExists() {
        Mockito.when(userRepository.findByUsername(loginUsernameDto.getUsername_email())).thenReturn(Optional.ofNullable(user));
    }

    @Test
    public void loginUserShouldLoginUserWhenUsernameIsCorrect() {
        expectUsernameExists();
        LoginResponse result = userService.loginUser(loginUsernameDto, request);
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(user.getUsername()));
    }

    public void expectUsernameDoesntExist() {
        Mockito.when(userRepository.findByUsername(loginUsernameDto.getUsername_email())).thenReturn(Optional.empty());
    }

    @Test
    public void loginUserShouldNotLoginUserWhenUsernameIsNotCorrect() {
        expectUsernameDoesntExist();
        LoginResponse result = userService.loginUser(loginUsernameDto, request);
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(user.getUsername()));
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LoginResponse.Status.INVALID_USERNAME));
    }

}