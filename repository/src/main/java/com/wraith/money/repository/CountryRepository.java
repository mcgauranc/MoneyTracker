package com.wraith.money.repository;

import com.wraith.money.data.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:34
 */
@RepositoryRestResource
public interface CountryRepository extends MongoRepository<Country, Long> {

    public Page<Country> findByIso(String iso, Pageable pageable);

}
