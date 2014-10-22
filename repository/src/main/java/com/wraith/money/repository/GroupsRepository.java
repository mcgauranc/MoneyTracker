package com.wraith.money.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.wraith.money.data.Groups;

/**
 * User: rowan.massey Date: 24/02/13 Time: 16:33
 */
@RestResource(rel = "groups", path = "/groups")
public interface GroupsRepository extends PagingAndSortingRepository<Groups, Long> {

	public List<Groups> findByName(@Param("groupName") String groupName);

}
