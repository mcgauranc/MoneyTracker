package com.wraith.repository.handler;

import com.wraith.repository.Encoding;
import com.wraith.repository.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.repository.annotation.HandleBeforeCreate;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

import java.security.NoSuchAlgorithmException;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 21:00
 */
@RepositoryEventHandler(Users.class)
public class UserEventHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Encoding encoding;

    @HandleBeforeCreate
    public void beforeUserCreate(Users user) {
        logger.debug(String.format("In before create for user '%s'", user.getUserFullName()));
        try {
            user.setPassword(encoding.encodePassword(user.getPassword(), user.getUserName()));
        } catch (NoSuchAlgorithmException e) {
            logger.error(String.format("Error encoding user password. Error was '%s'", e.getMessage()), e);
        }
    }
}
