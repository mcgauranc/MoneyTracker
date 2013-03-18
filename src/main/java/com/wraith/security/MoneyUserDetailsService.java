package com.wraith.security;

import org.apache.commons.lang.BooleanUtils;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = loadUserByUserName(username);
        if (user == null) {
            //logger.error(String.format("Query for username '%s' returned no results.", username));
            throw new UsernameNotFoundException(String.format("Query for user '%s' failed. User not found.", username));
        }

        Set<GrantedAuthority> authorities = new HashSet<>(getUserAuthorities(username));

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, true, authorities);
    }

    protected UserDetails loadUserByUserName(String username) {
        return getJdbcTemplate().queryForObject("select user_username, user_password, user_enabled from users where user_username = ?", new String[]{username}, new RowMapper<UserDetails>() {
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
        return getJdbcTemplate().query("select authorities_authority from authorities\n" +
                "join group_authorities on group_authorities_authorities_id = authorities_id\n" +
                "join user_groups on group_authorities_group_id = user_groups_group_id\n" +
                "join Users on user_id = user_groups_user_id\n" +
                "where user_userName = ?", new String[]{username}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String authorisation = rs.getString(1);
                return new SimpleGrantedAuthority(authorisation);
            }
        });
    }
}
