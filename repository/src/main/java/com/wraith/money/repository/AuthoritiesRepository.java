package com.wraith.money.repository;

import com.wraith.money.data.Authorities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: rowan.massey Date: 17/03/13 Time: 15:40
 */
@RepositoryRestResource(collectionResourceRel = "authorities", path = "/authorities")
public interface AuthoritiesRepository extends MongoRepository<Authorities, Long> {

	public Page<Authorities> findByAuthority(@Param("authorityName") String authorityName, Pageable pageable);

}
