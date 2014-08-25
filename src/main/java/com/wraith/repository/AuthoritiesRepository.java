package com.wraith.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.wraith.repository.entity.Authorities;

/**
 * User: rowan.massey
 * Date: 17/03/13
 * Time: 15:40
 */
@RestResource(rel = "authorities", path = "/authorities")
public interface AuthoritiesRepository extends CrudRepository<Authorities, Long> {

    public List<Authorities> findByAuthority(String authority);

}
