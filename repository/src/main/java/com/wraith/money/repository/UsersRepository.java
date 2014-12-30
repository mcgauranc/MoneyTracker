package com.wraith.money.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.wraith.money.data.Users;

/**
 * User: rowan.massey Date: 24/01/13 Time: 22:01
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "/users")
public interface UsersRepository extends MongoRepository<Users, String> {

	@RestResource
	public Users findByUserName(@Param("userName") String userName);

	@RestResource
	@Query(value = "{'userName': ?0}", count = true)
	public Long existsByUserName(@Param("userName") String userName);
}
