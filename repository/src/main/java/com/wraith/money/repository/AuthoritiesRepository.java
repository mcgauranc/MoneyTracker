package com.wraith.money.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.wraith.money.data.Authorities;

/**
 * User: rowan.massey Date: 17/03/13 Time: 15:40
 */
@RestResource(rel = "authorities", path = "/authorities")
public interface AuthoritiesRepository extends PagingAndSortingRepository<Authorities, Long> {

	public List<Authorities> findByAuthority(@Param("authorityName") String authorityName);

}
