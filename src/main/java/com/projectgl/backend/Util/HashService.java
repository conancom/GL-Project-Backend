package com.projectgl.backend.Util;

public interface HashService {

    public byte[] generateHash(String password, byte[] salt);
}
