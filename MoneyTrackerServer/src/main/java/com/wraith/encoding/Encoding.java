package com.wraith.encoding;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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

    public String encodePassword(String password, Object salt) throws NoSuchAlgorithmException {
        PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(password, salt);
    }

    public Boolean validPassword(String password, String encodedPassword, Object salt) throws NoSuchAlgorithmException {
        PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.isPasswordValid(encodedPassword, password, salt);
    }
}
