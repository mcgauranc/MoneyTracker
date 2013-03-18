package com.wraith.repository;

import com.wraith.repository.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("entityLoader")
public class EntityLoader {

    private final Logger logger = LoggerFactory.getLogger(EntityLoader.class);

    @Inject
    private UsersRepository usersRepository;
    @Inject
    private GroupsRepository groupsRepository;
    @Inject
    private Encoding encoding;

    @PostConstruct
    private void defaultDataLoader() {

        if (!userExists("rowan.massey")) {

            Country country = getDefaultCountry();

            Authorities adminAuthority = getAuthority("ROLE_ADMIN");
            Authorities userAuthority = getAuthority("ROLE_USER");

            Set<Authorities> adminAuthorities = new HashSet<>();
            adminAuthorities.add(adminAuthority);
            adminAuthorities.add(userAuthority);

            Set<Authorities> userAuthorities = new HashSet<>();
            userAuthorities.add(userAuthority);

            Groups adminGroup = createGroup("Administrators", adminAuthorities);
            createGroup("Users", userAuthorities);

            Set<Groups> adminGroups = new HashSet<>();
            adminGroups.add(adminGroup);

            createUser("Rowan", "Massey", "rowan.massey", "Passw0rd", adminGroups, country);
        }

    }

    /**
     * This method checks to see if there are any users on the database, before adding any of the demo data.
     *
     * @param userName The name of the user for which we're looking for.
     * @return True if there are no users on the database.
     */
    private Boolean userExists(String userName) {
        List<Users> users = usersRepository.findByUserName(userName);
        return users.size() > 0;
    }

    /**
     * This method returns a default country.
     *
     * @return An instance of a country.
     */
    private Country getDefaultCountry() {
        Country country = new Country();
        country.setIso("IRE");
        country.setName("Republic Of Ireland");
        return country;
    }

    /**
     * This method creates a new default address for a give country.
     *
     * @param country The country object that you wish to associate with address.
     * @return An instance of the address, for the given country.
     */
    private Address getDefaultAddress(Country country) {
        Address defaultAddress = new Address();
        defaultAddress.setAddress1("44 Stratton Walk");
        defaultAddress.setAddress2("Adamstown Square");
        defaultAddress.setAddress3("Lucan");
        defaultAddress.setCity("Dublin");
        defaultAddress.setCounty("Dublin");
        defaultAddress.setCountry(country);
        return defaultAddress;
    }

    /**
     * This method creates an instance of an authority.
     *
     * @param authorityName The name of the authority you wish to create.
     * @return An instance of the authority with the given name.
     */
    private Authorities getAuthority(String authorityName) {
        Authorities adminAuthority = new Authorities();
        adminAuthority.setAuthority(authorityName);
        return adminAuthority;
    }

    /**
     * This method creates an instance of a group, along with it's associated authorities.
     *
     * @param group       The name of the group that you wish to create.
     * @param authorities The list of authorities that you wish to associate with this group.
     * @return An instance of the group, with associated authorities.
     */
    private Groups createGroup(String group, Set<Authorities> authorities) {
        Groups adminGroup = new Groups();
        adminGroup.setName(group);
        adminGroup.setAuthorities(authorities);
        return groupsRepository.save(adminGroup);
    }

    /**
     * This method creates a default user on the database.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param userName  The user name for the user.
     * @param password  The users password.
     * @param groups    The group that the user is associated with.
     * @return An instance of the newly created user.
     */
    private Users createUser(String firstName, String lastName, String userName, String password, Set<Groups> groups, Country country) {
        Users defaultUser = new Users();
        defaultUser.setUserName(userName);
        defaultUser.setFirstName(firstName);
        defaultUser.setLastName(lastName);
        defaultUser.setDateOfBirth(Calendar.getInstance().getTime());
        defaultUser.setEnabled(1);
        try {
            defaultUser.setPassword(encoding.encodePassword(password, defaultUser.getUserName()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        defaultUser.setAddress(getDefaultAddress(country));
        defaultUser.setGroups(groups);
        return usersRepository.save(defaultUser);
    }
}
