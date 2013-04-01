package com.wraith.repository;

import com.wraith.repository.entity.Currency;
import org.springframework.data.repository.CrudRepository;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:32
 */
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
