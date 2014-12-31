package com.wraith.money.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.wraith.money.data.Groups;

/**
 * User: rowan.massey Date: 24/02/13 Time: 16:33
 */
@RepositoryRestResource(collectionResourceRel = "groups", path = "/groups")
public interface GroupsRepository extends MongoRepository<Groups, String> {

	public Groups findByName(@Param("groupName") String groupName);

}
