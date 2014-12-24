package com.wraith.money.repository;

import com.wraith.money.data.Groups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: rowan.massey Date: 24/02/13 Time: 16:33
 */
@RepositoryRestResource(itemResourceRel = "groups", path = "/groups")
public interface GroupsRepository extends MongoRepository<Groups, Long> {

	public Page<Groups> findByName(@Param("groupName") String groupName, Pageable pageable);

}
