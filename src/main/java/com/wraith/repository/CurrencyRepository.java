package com.wraith.repository;

import com.wraith.repository.entity.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:32
 */
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    public List<Currency> findByName(String currencyName);

}
