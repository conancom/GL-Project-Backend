package com.projectgl.backend.Util;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.nio.charset.StandardCharsets;

public class Argon2HashServiceImpl implements HashService{

    public byte[] generateHash(String password, byte[] salt) {
        int opsLimit = 4;
        int memLimit = 1048576;
        int outputLength = 32;
        int parallelism = 1;
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13)
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
}
