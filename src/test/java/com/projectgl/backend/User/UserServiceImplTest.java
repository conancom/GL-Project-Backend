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

    @Mock
    EntityManager entityManager;

    RegisterDto registerDto;

    @BeforeEach
    public void setUp() {
        registerDto = RegisterDto.builder().username("testUsername").email("testEmail@email.com").password("testPassword123").build();
    }

    private void expectUsernameIsInDatabase() {
        TypedQuery<User> query = (TypedQuery<User>) Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery("SELECT u.username FROM User u WHERE u.username = :username", String.class)).thenReturn(query);
        Mockito.when(query.setParameter("username", registerDto.getUsername())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(List.of(User.builder().build()));
    }

    @Test
    public void userNameExistsShouldReturnTrueWhenUserNameIsInDatabase() {
        expectUsernameIsInDatabase();
        boolean result = userService.userExistsUsername(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(true));
    }

    private void expectUsernameIsNotInDatabase() {
        TypedQuery<User> query = (TypedQuery<User>) Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery("SELECT u.username FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
        Mockito.when(query.setParameter("username", registerDto.getUsername())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());
    }

    @Test
    public void userNameExistsShouldReturnFalseWhenUserNameIsNotInDatabase() {
        expectUsernameIsNotInDatabase();
        boolean result = userService.userExistsUsername(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(false));
    }

    private void expectEmailIsInDatabase() {
        TypedQuery<User> query = (TypedQuery<User>) Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery("SELECT u.username FROM User u WHERE u.email = :email", User.class)).thenReturn(query);
        Mockito.when(query.setParameter("email", registerDto.getEmail())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(List.of(User.builder().build()));
    }

    @Test
    public void emailExistsShouldReturnTrueWhenEmailIsInDatabase() {
        expectEmailIsInDatabase();
        boolean result = userService.userExistsEmail(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(true));
    }

    private void expectEmailIsNotInDatabase() {
        TypedQuery<User> query = (TypedQuery<User>) Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery("SELECT u.username FROM User u WHERE u.email = :email", User.class)).thenReturn(query);
        Mockito.when(query.setParameter("email", registerDto.getEmail())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());
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