package com.projectgl.backend.User;

import com.projectgl.backend.Dto.RegisterDto;
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
import java.util.ArrayList;
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
        Mockito.when(entityManager.createQuery("SELECT u.username FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
        Mockito.when(query.setParameter("username", registerDto.getUsername())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(List.of(User.builder().build()));
    }

    @Test
    public void userNameExistsShouldReturnTrueWhenUserNameIsInDatabase(){
        expectUsernameIsInDatabase();
        boolean result = userService.userExistsUsername(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(true));
    }

    private void expectUsernameIsNotInDatabase() {
        TypedQuery<User> query = (TypedQuery<User>) Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery("SELECT u.username FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
        Mockito.when(query.setParameter("username", registerDto.getUsername())).thenReturn(query);
    }

    @Test
    public void userNameExistsShouldReturnFalseWhenUserNameIsNotInDatabase(){
        expectUsernameIsNotInDatabase();
        boolean result = userService.userExistsUsername(registerDto);
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(false));
    }

}