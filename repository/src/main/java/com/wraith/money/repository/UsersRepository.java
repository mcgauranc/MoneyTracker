package com.wraith.money.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.wraith.money.data.Users;

/**
 * User: rowan.massey Date: 24/01/13 Time: 22:01
 */
@RestResource(rel = "users", path = "/users")
public interface UsersRepository extends PagingAndSortingRepository<Users, Long> {

	List<Users> findByUserName(@Param("userName") String userName);

	@RestResource
	@Query("SELECT u FROM Users u WHERE concat(u.firstName, ' ', u.lastName) LIKE :fullName")
	List<Users> findByFullName(@Param("fullName") String firstName);

	@RestResource
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM Users u WHERE u.userName = :userName")
	Boolean existsByUserName(@Param("userName") String userName);

	List<Users> findAll();
}
