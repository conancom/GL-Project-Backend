package com.projectgl.backend.Controller;

import com.projectgl.backend.Dto.AllLibraryGamesDto;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccountService;
import com.projectgl.backend.Response.LibraryGamesResponse;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

//    @BeforeEach
//    public void setUp() {
//        allLibraryGamesDto = AllLibraryGamesDto.builder().session_id("testsession123").build();
//        user = User.builder().id(123L).build();
//    }
//
//    private void expectSessionIDNotFound() {
//        Mockito.when(httpReq.getSession())
//                .thenReturn(httpSession);
//        Mockito.when(httpSession.getAttribute(allLibraryGamesDto.getSession_id()))
//                .thenReturn(null);
//    }
//
//    @Test
//    public void testGetAllLibraryGamesWhenSessioIDDoesNotExistShouldReturnSessionExpired() {
//        expectSessionIDNotFound();
//        LibraryGamesResponse result = libraryController.getAllLibraryGames(allLibraryGamesDto, httpReq);
//        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LibraryGamesResponse.Status.SESSION_EXPIRED));
//    }
//
//    private void expectSessionIDFound() {
//        Mockito.when(httpReq.getSession())
//                .thenReturn(httpSession);
//        Mockito.when(httpSession.getAttribute(allLibraryGamesDto.getSession_id()))
//                .thenReturn(user.getId());
//    }
//
//    private void expectSessionAllLibraryGamesResponseCreated() {
//        Mockito.when(userService.createAllLibraryAccountResponse(user.getId()))
//                .thenReturn(LibraryGamesResponse.builder().status(LibraryGamesResponse.Status.SESSION_KEY_OK).build());
//    }
//
//    @Test
//    public void testGetAllLibraryGamesWhenSessioIDExistShouldReturnAllGamesLibraryAccountGames() {
//        expectSessionIDFound();
//        expectSessionAllLibraryGamesResponseCreated();
//        LibraryGamesResponse result = libraryController.getAllLibraryGames(allLibraryGamesDto, httpReq);
//        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.equalTo(LibraryGamesResponse.Status.SESSION_KEY_OK));
//    }

}