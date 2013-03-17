package com.wraith.repository;

import com.wraith.repository.entity.Country;
import org.springframework.data.repository.CrudRepository;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:34
 */
public interface CountryRepository  extends CrudRepository<Country, Integer> {

}
