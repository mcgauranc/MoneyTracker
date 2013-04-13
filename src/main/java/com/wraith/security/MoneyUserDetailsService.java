package com.wraith.security;

import com.wraith.repository.UsersRepository;
import com.wraith.repository.entity.Authorities;
import com.wraith.repository.entity.Groups;
import com.wraith.repository.entity.Users;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User: rowan.massey
 * Date: 12/08/12
 * Time: 21:44
 */
public class MoneyUserDetailsService extends JdbcDaoSupport implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug(String.format("Loading user with user name '%s'", username));

        UserDetails user = findUserByUsername(username);
        if (user == null) {
            String error = String.format("Query for user '%s' failed. User not found.", username);
            logger.error(error);
            throw new UsernameNotFoundException(error);
        }

        return user;
    }

    protected UserDetails findUserByUsername(String username) {
        logger.debug(String.format("Finding user with user name '%s'", username));

        Users user = usersRepository.findByUserName(username).get(0);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Groups group : user.getGroups()) {
            for (Authorities authority : group.getAuthorities()) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
                authorities.add(grantedAuthority);
            }
        }

        return new User(user.getUserName(), user.getPassword(), BooleanUtils.toBoolean(user.getEnabled()), true, true, true, authorities);
    }
}
