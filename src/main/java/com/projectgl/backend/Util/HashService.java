package com.projectgl.backend.Util;

public interface HashService {

    byte[] generateHash(String password, byte[] salt);
}
