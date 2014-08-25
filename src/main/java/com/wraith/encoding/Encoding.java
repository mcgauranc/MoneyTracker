package com.wraith.encoding;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

/**
 * User: rowan.massey
 * Date: 18/08/12
 * Time: 13:55
 */
@Component
public class Encoding {

    public Encoding() {

    }

    public String encodePassword(String password) throws NoSuchAlgorithmException {
        PasswordEncoder encoder = new StandardPasswordEncoder();
        return encoder.encode(password);
    }

    public Boolean validPassword(String password, String encodedPassword) throws NoSuchAlgorithmException {
        PasswordEncoder encoder = new StandardPasswordEncoder();
        return encoder.matches(encodedPassword, password);
    }
}
