package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;
import com.projectgl.backend.User.UserService;
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
    @Mock
    private HttpServletRequest httpReq;

    @BeforeEach
    public void setUp() {
        registerDto = RegisterDto.builder().username("testUsername").email("testEmail@email.com").password("testPassword123").build();
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
}