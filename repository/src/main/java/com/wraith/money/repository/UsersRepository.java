package com.wraith.money.repository;

import com.wraith.money.data.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * User: rowan.massey Date: 24/01/13 Time: 22:01
 */
@RestResource(rel = "users", path = "/users")
public interface UsersRepository extends PagingAndSortingRepository<Users, Long> {

	List<Users> findByUserName(@Param("userName") String userName);

	@RestResource
	@Query("SELECT u FROM Users u WHERE u.firstName LIKE CONCAT('%', :firstName, '%')")
	List<Users> findByFirstName(@Param("firstName") String firstName);

	@RestResource
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM Users u WHERE u.userName = :userName")
	Boolean existsByUserName(@Param("userName") String userName);

	List<Users> findAll();
}
