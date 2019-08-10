package com.canx.cebulax.cebulax.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String hashPassword(String password) {
        return new String(DigestUtils.sha256(password));
    }

    public boolean passwordsEquals(String plain, String hashed) {
        return hashPassword(plain).equals(hashed);
    }
}
