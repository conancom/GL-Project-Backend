package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.RegisterResponse;
import com.projectgl.backend.User.UserService;
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

import javax.servlet.http.HttpServletRequest;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    RegisterDto registerDto;

    LoginDto loginEmailDto;

    LoginDto loginUsernameDto;

    @Mock
    private HttpServletRequest httpReq;

    @BeforeEach
    public void setUp() {
        registerDto = RegisterDto.builder().username("testUsername").email("testEmail@email.com").password("testPassword123").build();
        loginEmailDto = LoginDto.builder().username_email("testEmail@email.com").password("testPassword123").build();
        loginUsernameDto = LoginDto.builder().username_email("testUsername").password("testPassword123").build();
    }

    private void expectUserDoesNotExist() {
        Mockito.doReturn(false).when(userService).userExistsUsername(registerDto);
        Mockito.doReturn(false).when(userService).userExistsEmail(registerDto);
        Mockito.doReturn(RegisterResponse.builder().status(RegisterResponse.Status.SUCCESS).username(registerDto.getUsername()).build()).when(userService).createUser(registerDto);
    }

    @Test
    public void testRegisterNewUserWhenUserDoesNotExist() {
        expectUserDoesNotExist();
        RegisterResponse result = userController.registerNewUser(registerDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(RegisterResponse.Status.SUCCESS));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(registerDto.getUsername()));
    }

    private void expectUsernameExists() {
        Mockito.doReturn(true).when(userService).userExistsUsername(registerDto);
    }

    @Test
    public void testRegisterNewUserWhenUsernameExists() {
        expectUsernameExists();
        RegisterResponse result = userController.registerNewUser(registerDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(RegisterResponse.Status.DUPLICATE_USERNAME));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(registerDto.getUsername()));
    }

    private void expectEmailExists() {
        Mockito.doReturn(true).when(userService).userExistsEmail(registerDto);
    }

    @Test
    public void testRegisterNewUserWhenEmailExists() {
        expectEmailExists();
        RegisterResponse result = userController.registerNewUser(registerDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(RegisterResponse.Status.DUPLICATE_EMAIL));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(registerDto.getUsername()));
    }

    private void expectLoginWithCorrectEmailCorrectPassword() {
        Mockito.doReturn(LoginResponse.builder().status(LoginResponse.Status.SUCCESS).username(loginEmailDto.getUsername_email()).session_id("12345Token").build()).when(userService).loginUser(loginEmailDto, httpReq);
    }

    @Test
    public void testLoginUserWithEmailCorrectEmailCorrectPassword(){
        expectLoginWithCorrectEmailCorrectPassword();
        LoginResponse result = userController.loginUser(loginEmailDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LoginResponse.Status.SUCCESS));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(loginEmailDto.getUsername_email()));
        MatcherAssert.assertThat(result.getSession_id(), CoreMatchers.equalTo("12345Token"));
    }

    private void expectLoginWithCorrectEmailWrongPassword() {
        Mockito.doReturn(LoginResponse.builder().status(LoginResponse.Status.INVALID_PASSWORD).username(loginEmailDto.getUsername_email()).build()).when(userService).loginUser(loginEmailDto, httpReq);
    }

    @Test
    public void testLoginUserWithEmailCorrectEmailWrongPassword(){
        expectLoginWithCorrectEmailWrongPassword();
        LoginResponse result = userController.loginUser(loginEmailDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LoginResponse.Status.INVALID_PASSWORD));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(loginEmailDto.getUsername_email()));
        MatcherAssert.assertThat(result.getSession_id(), CoreMatchers.equalTo(null));
    }

    private void expectLoginWithWrongEmail() {
        Mockito.doReturn(LoginResponse.builder().status(LoginResponse.Status.INVALID_EMAIL).username(loginEmailDto.getUsername_email()).build()).when(userService).loginUser(loginEmailDto, httpReq);
    }

    @Test
    public void testLoginUserWithWrongEmail(){
        expectLoginWithWrongEmail();
        LoginResponse result = userController.loginUser(loginEmailDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LoginResponse.Status.INVALID_EMAIL));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(loginEmailDto.getUsername_email()));
        MatcherAssert.assertThat(result.getSession_id(), CoreMatchers.equalTo(null));
    }

    private void expectLoginWithCorrectUsernameCorrectPassword() {
        Mockito.doReturn(LoginResponse.builder().status(LoginResponse.Status.SUCCESS).username(loginUsernameDto.getUsername_email()).session_id("12345Token").build()).when(userService).loginUser(loginUsernameDto, httpReq);
    }

    @Test
    public void testLoginUserWithUsernameCorrectEmailCorrectPassword(){
        expectLoginWithCorrectUsernameCorrectPassword();
        LoginResponse result = userController.loginUser(loginUsernameDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LoginResponse.Status.SUCCESS));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(loginUsernameDto.getUsername_email()));
        MatcherAssert.assertThat(result.getSession_id(), CoreMatchers.equalTo("12345Token"));
    }

    private void expectLoginWithCorrectUsernameWrongPassword() {
        Mockito.doReturn(LoginResponse.builder().status(LoginResponse.Status.INVALID_PASSWORD).username(loginUsernameDto.getUsername_email()).build()).when(userService).loginUser(loginUsernameDto, httpReq);
    }

    @Test
    public void testLoginUserWithCorrectUsernameWrongPassword(){
        expectLoginWithCorrectUsernameWrongPassword();
        LoginResponse result = userController.loginUser(loginUsernameDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LoginResponse.Status.INVALID_PASSWORD));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(loginUsernameDto.getUsername_email()));
        MatcherAssert.assertThat(result.getSession_id(), CoreMatchers.equalTo(null));
    }

    private void expectLoginWithWrongUsername() {
        Mockito.doReturn(LoginResponse.builder().status(LoginResponse.Status.INVALID_USERNAME).username(loginUsernameDto.getUsername_email()).build()).when(userService).loginUser(loginUsernameDto, httpReq);
    }

    @Test
    public void testLoginUserWithUsernameWrongUsername(){
        expectLoginWithWrongUsername();
        LoginResponse result = userController.loginUser(loginUsernameDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LoginResponse.Status.INVALID_USERNAME));
        MatcherAssert.assertThat(result.getUsername(), CoreMatchers.equalTo(loginUsernameDto.getUsername_email()));
        MatcherAssert.assertThat(result.getSession_id(), CoreMatchers.equalTo(null));
    }
}