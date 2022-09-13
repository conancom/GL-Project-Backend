package com.projectgl.backend.User;

import com.projectgl.backend.Dto.LoginDto;
import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.LoginResponse;
import com.projectgl.backend.Response.RegisterResponse;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    final public UserRepository userRepository;
    final public EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    public static byte[] generateArgon2(String password, byte[] salt) {
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
        byte[] salt = generateSalt16Byte();
        String securedPassword = base64Encoding(generateArgon2(registerDto.getPassword(), salt));
        String saltString = base64Encoding(salt);
        //byte[] saltEnc = Base64.getDecoder().decode(saltString);
        User user = new User(registerDto.getUsername(), registerDto.getEmail(), securedPassword, saltString);
        userRepository.save(user);
        return RegisterResponse.builder().status(RegisterResponse.Status.SUCCESS).username(registerDto.getUsername()).build();
    }

    @Override
    public LoginResponse loginUser(LoginDto loginDto) {
        Optional<User> user;
        if (isEmail(loginDto.getUsername_email())) {
            user = userRepository.findByEmail(loginDto.getUsername_email());
            if (!user.isPresent()) {
                return LoginResponse.builder().username(user.get().getUsername()).status(LoginResponse.Status.INVALID_EMAIL).build();
            }
        } else {
            user = userRepository.findByUsername(loginDto.getUsername_email());
            if (!user.isPresent()) {
                return LoginResponse.builder().username(user.get().getUsername()).status(LoginResponse.Status.INVALID_USERNAME).build();
            }
        }
        byte[] salt = Base64.getDecoder().decode(user.get().getSalt());
        String inputPassword = base64Encoding(generateArgon2(loginDto.getPassword(), salt));
        if (inputPassword.equals(user.get().getPassword())) {
            return LoginResponse.builder().username(user.get().getUsername()).status(LoginResponse.Status.SUCCESS).build();
        }
        return LoginResponse.builder().username(user.get().getUsername()).status(LoginResponse.Status.INVALID_PASSWORD).build();
    }

    @Override
    public String createToken() {
        return  String.valueOf(System.currentTimeMillis()).substring(8, 13) + UUID.randomUUID().toString().substring(1,10);
    }

    private boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }
}
