package com.wraith.money.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.wraith.money.data.Currency;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:32
 */
@RepositoryRestResource
public interface CurrencyRepository extends MongoRepository<Currency, String> {

    public Page<Currency> findByIso(@Param("currencyIso") String currencyIso, Pageable pageable);

}
