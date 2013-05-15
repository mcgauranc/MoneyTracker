package com.wraith.repository.handler;

import com.wraith.encoding.Encoding;
import com.wraith.exception.MoneyException;
import com.wraith.repository.GroupsRepository;
import com.wraith.repository.entity.Groups;
import com.wraith.repository.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.repository.annotation.HandleBeforeCreate;
import org.springframework.data.rest.repository.annotation.HandleBeforeDelete;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

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
    @Autowired
    private GroupsRepository groupsRepository;

    @HandleBeforeCreate
    public void beforeUserCreate(Users user) {
        logger.debug(String.format("In before create for user '%s'", user.getUserFullName()));

        user.setEnabled(1);
        user.setGroups(getUserRoleGroup());
        user.setPassword(getCreatedUserPassword(user.getUserName(), user.getPassword()));
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #user.getUserName() == authentication.name))")
    @HandleBeforeSave
    public void beforeUserUpdate(Users user) {
        //Don't need to add anything to this method, the @PreAuthorize does the job.
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #user.getUserName() == authentication.name))")
    @HandleBeforeDelete
    public void beforeUserDelete(Users user) {
        //Don't need to add anything to this method, the @PreAuthorize does the job.
    }

    /**
     * This method encodes the user's password
     *
     * @param userName The userName of the created user.
     * @param password The original password of the create user.
     */
    private String getCreatedUserPassword(String userName, String password) {
        logger.debug(String.format("Encoding password for user '%s'", userName));
        try {
            return encoding.encodePassword(password, userName);
        } catch (NoSuchAlgorithmException e) {
            logger.error(String.format("Error encoding user password. Error was '%s'", e.getMessage()), e);
            throw new MoneyException(String.format("There was an error encoding the user password for user '%s'", userName));
        }
    }

    /**
     * This method searches for the "Users" group in the groups repository.
     *
     * @return A set, containing the found users group.
     */
    private Set<Groups> getUserRoleGroup() {
        Set<Groups> userGroups = new HashSet<>();
        Groups userGroup = groupsRepository.findByName("Users").get(0);
        userGroups.add(userGroup);
        return userGroups;
    }
}
