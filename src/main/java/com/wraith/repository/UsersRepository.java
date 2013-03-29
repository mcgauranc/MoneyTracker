package com.wraith.repository;

import com.wraith.repository.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import java.util.List;

/**
 * User: rowan.massey
 * Date: 24/01/13
 * Time: 22:01
 */
@RestResource(rel = "user", path = "user")
public interface UsersRepository extends PagingAndSortingRepository<Users, Integer> {

    @RestResource(path = "username", rel = "username")
    public List<Users> findByUserName(@Param("username") String username);

    @RestResource(rel = "enabled")
    public Page<Users> findByEnabledIsTrue(Pageable pageable);

    @RestResource(rel = "disabled")
    public Page<Users> findByEnabledIsFalse(Pageable pageable);
}
