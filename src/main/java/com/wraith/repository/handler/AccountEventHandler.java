package com.wraith.repository.handler;

import com.wraith.repository.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.repository.annotation.HandleBeforeCreate;
import org.springframework.data.rest.repository.annotation.HandleBeforeDelete;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * User: rowan.massey
 * Date: 20/04/13
 * Time: 16:00
 */
@RepositoryEventHandler(Account.class)
public class AccountEventHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @HandleBeforeCreate
    public void beforeAccountCreate(Account account) {
        logger.debug(String.format("In before create for account '%s'", account.getName()));
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN'))")
    @HandleBeforeSave
    public void beforeAccountUpdate(Account account) {
        logger.debug(String.format("In before update for account '%s'", account.getName()));
        //Don't need to add anything to this method, the @PreAuthorize does the job.
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN'))")
    @HandleBeforeDelete
    public void beforeAccountDelete(Account account) {
        logger.debug(String.format("In before delete for account '%s'", account.getName()));
        //Don't need to add anything to this method, the @PreAuthorize does the job.
    }

}
