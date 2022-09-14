package com.projectgl.backend.User;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;
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
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    RegisterDto registerDto;

    @BeforeEach
    public void setUp() {
        registerDto = RegisterDto.builder().username("testUsername").email("testEmail@email.com").password("testPassword123").build();
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
}