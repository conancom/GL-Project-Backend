package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.AllLibraryGamesDto;
import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccountService;
import com.projectgl.backend.Response.AllLibraryGamesResponse;
import com.projectgl.backend.Response.RegisterResponse;
import com.projectgl.backend.User.User;
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
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LibraryControllerTest {

    @InjectMocks
    LibraryController libraryController;

    @Mock
    RegisteredLibraryAccountService registeredLibraryAccountService;

    @Mock
    UserService userService;

    @Mock
    HttpServletRequest httpReq;

    @Mock
    HttpSession httpSession;

    AllLibraryGamesDto allLibraryGamesDto;

    User user;

    @BeforeEach
    public void setUp() {
        allLibraryGamesDto = AllLibraryGamesDto.builder().session_id("testsession123").build();
        user = User.builder().id(123L).build();
    }

    private void expectSessionIDNotFound() {
        Mockito.when(httpReq.getSession())
                .thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute(allLibraryGamesDto.getSession_id()))
                .thenReturn(null);
    }

    @Test
    public void testGetAllLibraryGamesWhenSessioIDDoesNotExistShouldReturnSessionExpired() {
        expectSessionIDNotFound();
        AllLibraryGamesResponse result = libraryController.getAllLibraryGames(allLibraryGamesDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(AllLibraryGamesResponse.Status.SESSION_EXPIRED));
    }

    private void expectSessionIDFound() {
        Mockito.when(httpReq.getSession())
                .thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute(allLibraryGamesDto.getSession_id()))
                .thenReturn(user.getId());
    }

    private void expectSessionAllLibraryGamesResponseCreated() {
        Mockito.when(userService.createAllLibraryAccountResponse(user.getId()))
                .thenReturn(AllLibraryGamesResponse.builder().status(AllLibraryGamesResponse.Status.SESSION_KEY_OK).build());
    }

    @Test
    public void testGetAllLibraryGamesWhenSessioIDExistShouldReturnAllGamesLibraryAccountGames() {
        expectSessionIDFound();
        expectSessionAllLibraryGamesResponseCreated();
        AllLibraryGamesResponse result = libraryController.getAllLibraryGames(allLibraryGamesDto, httpReq);
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(AllLibraryGamesResponse.Status.SESSION_KEY_OK));
    }

}