package com.projectgl.backend.User;

import com.projectgl.backend.Dto.GameDetail;
import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformation;
import com.projectgl.backend.PersonalGameInformation.PersonalGameInformationService;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccountService;
import com.projectgl.backend.Response.LibraryGamesResponse;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.RegisterResponse;
import com.projectgl.backend.Session.SessionService;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    final public UserRepository userRepository;

    final public EntityManager entityManager;

    final public RegisteredLibraryAccountService registeredLibraryAccountService;

    final PersonalGameInformationService personalGameInformationService;

    final public SessionService sessionService;

    final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager, RegisteredLibraryAccountService registeredLibraryAccountService, PersonalGameInformationService personalGameInformationService, SessionService sessionService) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.registeredLibraryAccountService = registeredLibraryAccountService;
        this.personalGameInformationService = personalGameInformationService;
        this.sessionService = sessionService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    private static byte[] generateSalt16Byte() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private static String base64Encoding(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    public boolean userExistsUsername(RegisterDto registerDto) {
        return userRepository.existsByUsername(registerDto.getUsername());
    }

    public boolean userExistsEmail(RegisterDto registerDto) {
        return userRepository.existsByEmail(registerDto.getEmail());
    }

    public RegisterResponse createUser(RegisterDto registerDto) {

//        byte[] salt = generateSalt16Byte();
//        String securedPassword = base64Encoding(generateHash(registerDto.getPassword(), salt));
//        String saltString = base64Encoding(salt);
        String securedPassword = passwordEncoder.encode(registerDto.getPassword());

        User user = User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(securedPassword)
                .registrationTimeStamp(LocalDateTime.now())
                .updateTimeStamp(LocalDateTime.now())
                .lastLoginTimeStamp(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return RegisterResponse.builder().status(RegisterResponse.Status.SUCCESS).username(registerDto.getUsername()).build();
    }

    public LoginResponse loginUser(LoginDto loginDto, HttpServletRequest request) {
        Optional<User> user;

        if (isEmail(loginDto.getUsername_email())) {
            user = userRepository.findByEmail(loginDto.getUsername_email());
            if (user.isEmpty()) {
                return LoginResponse.builder().username(loginDto.getUsername_email()).status(LoginResponse.Status.INVALID_EMAIL).build();
            }
        } else {
            user = userRepository.findByUsername(loginDto.getUsername_email());
            if (user.isEmpty()) {
                return LoginResponse.builder().username(loginDto.getUsername_email()).status(LoginResponse.Status.INVALID_USERNAME).build();
            }
        }

//        byte[] salt = Base64.getDecoder().decode(user.get().getSalt());
//        String inputPassword = base64Encoding(generateHash(loginDto.getPassword(), salt));
        //String inputPassword = passwordEncoder.encode(loginDto.getPassword());
        //if (!inputPassword.equals(user.get().getPassword())) {
        if (!passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            return LoginResponse.builder().username(user.get().getUsername()).status(LoginResponse.Status.INVALID_PASSWORD).build();
        }

        String token = createToken();
        sessionService.createSession(token, user.get().getId());
        return LoginResponse.builder().username(user.get().getUsername()).status(LoginResponse.Status.SUCCESS).session_id(token).build();
    }

    public Optional<User> findUserbyId(long userId) {
        return userRepository.findById(userId);
    }

    public LibraryGamesResponse createAllLibraryAccountResponse(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        LibraryGamesResponse libraryGamesResponse;
        if (user.isEmpty()) {
            libraryGamesResponse = LibraryGamesResponse.builder().status(LibraryGamesResponse.Status.USER_DOES_NOT_EXIST).build();
            return libraryGamesResponse;
        }
        List<RegisteredLibraryAccount> registeredLibraryAccounts = user.get().getRegisteredLibraryAccountList();
        libraryGamesResponse = LibraryGamesResponse.builder().build();
        libraryGamesResponse.setGames(new ArrayList<>());
        registeredLibraryAccounts.forEach(registeredLibraryAccount -> {

            if(registeredLibraryAccount.getAccountType().equals("STEAM")){
                registeredLibraryAccountService.synchronizeSteamRegisteredLibraryAccount(registeredLibraryAccount);
            }

            Optional<RegisteredLibraryAccount> newRegisteredLibraryAccount = registeredLibraryAccountService.findRegisteredLibraryAccountById(registeredLibraryAccount.getId());

            List<PersonalGameInformation> personalGameInformations = newRegisteredLibraryAccount.orElseThrow().getPersonalGameInformationList();
            personalGameInformations.forEach(personalGameInformation -> {
                GameDetail gameDetail = GameDetail.builder()
                        .game_name(personalGameInformation.getGame().getName())
                        .personal_game_id(personalGameInformation.getId())
                        .game_id(personalGameInformation.getGame().getId())
                        .summary(personalGameInformation.getGame().getSummary())
                        .storyline(personalGameInformation.getGame().getStoryline())
                        .rating(personalGameInformation.getGame().getRating())
                        .first_release_date(personalGameInformation.getGame().getFirst_release_date())
                        .picture_url(personalGameInformation.getGame().getProfileImg())
                        .banner_url(personalGameInformation.getGame().getBackgroundImg())
                        .library_name(registeredLibraryAccount.getAccountType())
                        .library_id(registeredLibraryAccount.getId())
                        .total_play_time(personalGameInformation.getTotaltimeplayed())
                        .build();
                libraryGamesResponse.getGames().add(gameDetail);
            });
        });
        libraryGamesResponse.getGames().sort(Comparator.comparing(GameDetail::getGame_name));
        libraryGamesResponse.setStatus(LibraryGamesResponse.Status.SESSION_KEY_OK);
        return libraryGamesResponse;
    }

    public static String createToken() {
        return String.valueOf(System.currentTimeMillis()).substring(8, 13) + UUID.randomUUID().toString().substring(1, 12);
    }

    private boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }

    public static byte[] generateHash(String password, byte[] salt) {
        int opsLimit = 4;
        int memLimit = 1048576;
        int outputLength = 32;
        int parallelism = 1;
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id).withVersion(Argon2Parameters.ARGON2_VERSION_13).withIterations(opsLimit).withMemoryAsKB(memLimit).withParallelism(parallelism).withSalt(salt);
        Argon2BytesGenerator gen = new Argon2BytesGenerator();
        gen.init(builder.build());
        byte[] result = new byte[outputLength];
        gen.generateBytes(password.getBytes(StandardCharsets.UTF_8), result, 0, result.length);
        return result;
    }
}
