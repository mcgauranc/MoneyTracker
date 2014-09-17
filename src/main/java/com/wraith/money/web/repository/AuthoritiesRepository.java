package com.wraith.money.web.repository;

import com.wraith.money.web.repository.entity.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * User: rowan.massey
 * Date: 17/03/13
 * Time: 15:40
 */
@RestResource(rel = "authorities", path = "/authorities")
public interface AuthoritiesRepository extends CrudRepository<Authorities, Long> {

    public List<Authorities> findByAuthority(String authority);

}
