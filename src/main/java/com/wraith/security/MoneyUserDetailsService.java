package com.wraith.security;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: rowan.massey
 * Date: 12/08/12
 * Time: 21:44
 */
public class MoneyUserDetailsService extends JdbcDaoSupport implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug(String.format("Loading user with user name '%s'", username));

        UserDetails user = findUserByUsername(username);
        if (user == null) {
            String error = String.format("Query for user '%s' failed. User not found.", username);
            logger.error(error);
            throw new UsernameNotFoundException(error);
        }

        Set<GrantedAuthority> authorities = new HashSet<>(getUserAuthorities(username));

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, true, authorities);
    }

    protected UserDetails findUserByUsername(String username) {
        logger.debug(String.format("Finding user with user name '%s'", username));

        return getJdbcTemplate().queryForObject("select users_username, users_password, users_enabled from users where users_enabled = 1 and" +
                " users_username = ?", new String[]{username}, new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = BooleanUtils.toBoolean(rs.getInt(3));
                return new User(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
            }
        });
    }

    protected List<GrantedAuthority> getUserAuthorities(String username) {
        logger.debug(String.format("Getting user authorities for user '%s'", username));

        return getJdbcTemplate().query("select authorities_authority from authorities\n" +
                "join group_authorities on group_authorities_authorities_id = authorities_id\n" +
                "join users_groups on group_authorities_group_id = users_groups_group_id\n" +
                "join Users on users_id = users_groups_user_id\n" +
                "where users_enabled = 1 and users_userName = ?", new String[]{username}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String authorisation = rs.getString(1);
                return new SimpleGrantedAuthority(authorisation);
            }
        });
    }
}
