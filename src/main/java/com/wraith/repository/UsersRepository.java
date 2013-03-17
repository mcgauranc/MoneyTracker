package com.wraith.repository;

import com.wraith.repository.entity.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import java.util.List;

/**
 * User: rowan.massey
 * Date: 24/01/13
 * Time: 22:01
 */
@RestResource(path = "user")
public interface UsersRepository extends PagingAndSortingRepository<Users, Integer> {

    @RestResource(path = "username", rel = "username")
    public List<Users> findByUserName(@Param("username") String username);

    //@RestResource(rel = "enabled")
//    public Page<User> findByEnabledIsTrue(Pageable pageable);
//
//    public List<User> findByCreatedAfter(@Param("when") @ConvertWith(ISO8601StringToDateConverter.class) Date when);
}
