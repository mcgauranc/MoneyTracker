package com.wraith.money.repository;

import com.wraith.money.data.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * User: rowan.massey
 * Date: 24/01/13
 * Time: 22:01
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "/users")
public interface UsersRepository extends MongoRepository<Users, Long> {

    @RestResource
    public Users findByUserName(@Param("userName") String userName);

    @RestResource
    //@Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM Users u WHERE u.userName = :userName")
    @Query(value = "{'userName': ?0}", count = true)
    public Long existsByUserName(@Param("userName") String userName);
}
