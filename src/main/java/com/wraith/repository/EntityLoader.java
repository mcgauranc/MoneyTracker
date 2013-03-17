package com.wraith.repository;

import com.wraith.repository.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;

@Named("entityLoader")
public class EntityLoader {

  private final Logger logger = LoggerFactory.getLogger(EntityLoader.class);

  @Inject
  private AccountRepository account;
  @Inject
  private UsersRepository user;

  @PostConstruct
  private void load() {

      Users newUser = new Users();
      newUser.setFirstName("Rowan");
      newUser.setLastName("Massey");
      newUser.setDateOfBirth(Calendar.getInstance().getTime());
      newUser.setEnabled(1);
      newUser.setPassword("Passw0rd");
      newUser.setUserName("rowan.massey");

      user.save(newUser);

      Users newUser1 = new Users();
      newUser1.setFirstName("Saran");
      newUser1.setLastName("Massey");
      newUser1.setDateOfBirth(Calendar.getInstance().getTime());
      newUser1.setEnabled(1);
      newUser1.setPassword("Passw0rd");
      newUser1.setUserName("sarah.massey");

      user.save(newUser1);

      logger.debug(String.format("User '%s' was successfully saved.", newUser.getUserName()));
//    Address addr = addresses.save(new Address(
//        Arrays.asList("123 W. 1st St."),
//        "Univille",
//        "US",
//        "12345"
//    ));
//    logger.info("  **** Saved Address: " + addr);
//
//    Profile twitter = profiles.save(new Profile("twitter", "http://twitter.com/john_doe"));
//    logger.info("  **** Saved Profile: " + twitter);
//
//    Map<String, Profile> profs = new HashMap<String, Profile>();
//    profs.put("twitter", twitter);
//
//    Person johnDoe = people.save(new Person(
//        "John Doe",
//        Arrays.asList(addr),
//        profs
//    ));
//    logger.info("  **** Saved Person: " + johnDoe);

  }

}
