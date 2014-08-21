package com.wraith.configuration;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wraith.encoding.Encoding;
import com.wraith.repository.AuthoritiesRepository;
import com.wraith.repository.GroupsRepository;
import com.wraith.repository.UsersRepository;
import com.wraith.repository.entity.Address;
import com.wraith.repository.entity.Authorities;
import com.wraith.repository.entity.Country;
import com.wraith.repository.entity.Groups;
import com.wraith.repository.entity.Users;

@Component("entityLoader")
public class EntityLoader {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private UsersRepository usersRepository;
	@Inject
	private GroupsRepository groupsRepository;
	@Inject
	private AuthoritiesRepository authoritiesRepository;

	@Inject
	private Encoding encoding;

	@PostConstruct
	private void defaultDataLoader() {

		if (!usersExist()) {
			Authorities adminAuthority = getAuthority("ROLE_ADMIN");
			Authorities userAuthority = getAuthority("ROLE_USER");

			//Set the authorities for the administrator group.
			Set<Authorities> adminAuthorities = new HashSet<>();
			adminAuthorities.add(adminAuthority);
			adminAuthorities.add(userAuthority);

			//Create all the groups that the application supports.
			Groups adminGroup = createGroup("Administrators", adminAuthorities);

			Set<Groups> adminGroups = new HashSet<>();
			adminGroups.add(adminGroup);

			createUser("Admin", "User", "Administrator", "Passw0rd", "IRL", "Republic of Ireland", adminGroups);

//			//Set the authorities for the user group.
//			Set<Authorities> userAuthorities = new HashSet<>();
//			userAuthorities.add(authoritiesRepository.findByAuthority("ROLE_USER").get(0));
//
//			Groups userGroup = createGroup("Users", userAuthorities);
//
//			Set<Groups> userGroups = new HashSet<>();
//			userGroups.add(userGroup);
//
//			createUser("One", "User", "User1", "Passw0rd", "UK", "United Kingdom", userGroups);
		}
	}

	/**
	 * This method checks to see if there are any users on the database, before adding any of the demo data.
	 *
	 * @return True if there are no users on the database.
	 */
	private Boolean usersExist() {
		Boolean result = false;
		for (Users user : usersRepository.findAll()) {
			result = !user.getUserName().isEmpty();
			break;
		}
		return result;
	}

	/**
	 * This method creates an instance of an authority.
	 *
	 * @param authorityName
	 *            The name of the authority you wish to create.
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
	 * @param group
	 *            The name of the group that you wish to create.
	 * @param authorities
	 *            The list of authorities that you wish to associate with this group.
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
	 * @param firstName
	 *            The first name of the user.
	 * @param lastName
	 *            The last name of the user.
	 * @param userName
	 *            The user name for the user.
	 * @param password
	 *            The users password.
	 * @param groups
	 *            The group that the user is associated with.
	 * @return An instance of the newly created user.
	 */
	private Users createUser(String firstName, String lastName, String userName, String password, String countryISO,
			String countryName, Set<Groups> groups) {
		Users defaultUser = new Users();
		defaultUser.setUserName(userName);
		defaultUser.setFirstName(firstName);
		defaultUser.setLastName(lastName);
		defaultUser.setDateOfBirth(Calendar.getInstance().getTime());
		defaultUser.setEnabled(1);
		try {
			defaultUser.setPassword(encoding.encodePassword(password));
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error encoding password for default user.", e);
		}
		defaultUser.setGroups(groups);
		Country country = new Country();
		country.setIso(countryISO);
		country.setName(countryName);
		Address address = new Address();
		address.setAddress1("Address 1");
		address.setAddress2("Address 2");
		address.setAddress3("Address 3");
		address.setAddress4("Address 4");
		address.setCity("Lucan");
		address.setCounty("Dublin");
		address.setCountry(country);
		defaultUser.setAddress(address);
		return usersRepository.save(defaultUser);
	}
}
