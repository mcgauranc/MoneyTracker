package com.wraith.repository;

import com.wraith.repository.entity.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:33
 */
@RestResource(path = "Groups")
public interface GroupsRepository extends CrudRepository<Groups, Integer> {
}
