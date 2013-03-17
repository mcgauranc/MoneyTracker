package com.wraith.repository.handlers;

import com.wraith.repository.entity.Users;
import org.springframework.data.rest.repository.annotation.HandleAfterSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 21:00
 */
@Component
@RepositoryEventHandler(Users.class)
public class UserAfterSaveHandler {

    @HandleAfterSave
    public void afterSave(Users user) {
        System.out.println("Saved a user.");
    }
}
