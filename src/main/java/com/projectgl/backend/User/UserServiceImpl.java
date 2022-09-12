package com.projectgl.backend.User;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {

    final public UserRepository userRepository;
    final public EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    public boolean userExistsUsername(RegisterDto registerDto) {
        return userRepository.existsByUsername(registerDto.getUsername());
    }

    public boolean userExistsEmail(RegisterDto registerDto) {
        return userRepository.existsByEmail(registerDto.getEmail());
    }

    public RegisterResponse createUser(RegisterDto registerDto) {
        byte[] salt = generateSalt16Byte();
        String securedPassword = base64Encoding(generateArgon2idSensitive(registerDto.getPassword(), salt));
        String saltString = base64Encoding(salt);
        //byte[] saltEnc = Base64.getDecoder().decode(saltString);
        User user = new User(registerDto.getUsername(), registerDto.getEmail(), securedPassword, saltString);
        userRepository.save(user);
        return RegisterResponse.builder().status(RegisterResponse.Status.SUCCESS).username(registerDto.getUsername()).build();
    }

    public static byte[] generateArgon2idSensitive(String password, byte[] salt) {
        int opsLimit = 4;
        int memLimit = 1048576;
        int outputLength = 32;
        int parallelism = 1;
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) // 19
                .withIterations(opsLimit)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt);
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
}
