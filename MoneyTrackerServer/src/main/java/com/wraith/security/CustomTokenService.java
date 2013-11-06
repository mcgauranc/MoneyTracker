package com.wraith.security;

/**
 * User: rowan.massey
 * Date: 29/09/13
 * Time: 17:08
 */

import com.wraith.repository.CustomTokenRepository;
import com.wraith.repository.entity.PersistentLogin;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;

@Component
public class CustomTokenService implements PersistentTokenRepository {

    @Inject
    private CustomTokenRepository tokenRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin login = getPersistentLogin(token);
        tokenRepository.save(login);
    }

    private PersistentLogin getPersistentLogin(PersistentRememberMeToken token) {
        PersistentLogin login = new PersistentLogin();
        login.setUserName(token.getUsername());
        login.setLastUsed(token.getDate());
        login.setSeries(token.getSeries());
        login.setToken(token.getTokenValue());
        return login;
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        //tokenRepository.save(token);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        PersistentLogin login = tokenRepository.findOne(seriesId);
        if (login == null) {
            return null;
        }
        return new PersistentRememberMeToken(login.getUserName(),
                login.getSeries(), login.getToken(), login.getLastUsed());
    }

    @Override
    public void removeUserTokens(String username) {
        //tokenRepository.delete();
    }
}