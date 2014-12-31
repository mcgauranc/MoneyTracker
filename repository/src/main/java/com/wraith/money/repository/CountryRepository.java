package com.wraith.money.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.wraith.money.data.Country;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:34
 */
@RepositoryRestResource
public interface CountryRepository extends MongoRepository<Country, String> {

    public Page<Country> findByIso(String iso, Pageable pageable);

}
