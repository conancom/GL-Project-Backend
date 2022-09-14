package com.projectgl.backend.Utils;

public interface HashService {

    public byte[] generateHash(String password, byte[] salt);
}
