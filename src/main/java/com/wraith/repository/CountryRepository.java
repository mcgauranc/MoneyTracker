package com.wraith.repository;

import com.wraith.repository.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:34
 */
public interface CountryRepository extends CrudRepository<Country, Long> {

    public List<Country> findByIso(String iso);

}
