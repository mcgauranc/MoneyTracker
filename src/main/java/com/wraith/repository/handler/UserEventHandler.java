package com.wraith.repository.handler;

import com.wraith.repository.Encoding;
import com.wraith.repository.GroupsRepository;
import com.wraith.repository.entity.Groups;
import com.wraith.repository.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.repository.annotation.HandleBeforeCreate;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;

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
        setCreatedUserPassword(user);
        setCreatedUserGroup(user);
    }

    /**
     * This method encodes the user's password
     *
     * @param user The user object containing the user's password.
     */
    private void setCreatedUserPassword(Users user) {
        logger.debug(String.format("Encoding password for user '%s'", user.getUserFullName()));
        try {
            user.setPassword(encoding.encodePassword(user.getPassword(), user.getUserName()));
        } catch (NoSuchAlgorithmException e) {
            logger.error(String.format("Error encoding user password. Error was '%s'", e.getMessage()), e);
        }
    }

    /**
     * This method gets the "Users" group, and assigns it to the newly created user.
     *
     * @param user The user object to which the group needs to be added to.
     */
    private void setCreatedUserGroup(Users user) {
        Set<Groups> groups = getUserRoleGroup();
        user.setGroups(groups);
    }

    /**
     * This method searches for the "Users" group in the groups repository.
     *
     * @return A set, containing the found users group.
     */
    private Set<Groups> getUserRoleGroup() {
        Set<Groups> userGroup = new HashSet<>();
        userGroup.add(groupsRepository.findByName("Users").get(0));
        return userGroup;
    }
}
