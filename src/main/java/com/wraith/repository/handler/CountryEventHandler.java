package com.wraith.repository.handler;

import com.wraith.repository.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.repository.annotation.HandleBeforeCreate;
import org.springframework.data.rest.repository.annotation.HandleBeforeDelete;
import org.springframework.data.rest.repository.annotation.HandleBeforeSave;
import org.springframework.data.rest.repository.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * User: rowan.massey
 * Date: 05/05/13
 * Time: 21:04
 */
@RepositoryEventHandler(Country.class)
public class CountryEventHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN'))")
    @HandleBeforeCreate
    public void beforeCountryCreate(Country country) {
        logger.debug(String.format("In before create for authority '%s'", country.getName()));
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN'))")
    @HandleBeforeSave
    public void beforeCountryUpdate(Country country) {
        logger.debug(String.format("In before update for category '%s'", country.getName()));
        //Don't need to add anything to this method, the @PreAuthorize does the job.
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN'))")
    @HandleBeforeDelete
    public void beforeCountryDelete(Country country) {
        logger.debug(String.format("In before delete for authority '%s'", country.getName()));
        //Don't need to add anything to this method, the @PreAuthorize does the job.
    }

}
