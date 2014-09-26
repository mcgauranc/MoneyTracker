package com.wraith.money.repository;

import com.wraith.money.data.Currency;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:32
 */
public interface CurrencyRepository extends PagingAndSortingRepository<Currency, Long> {

    public List<Currency> findByName(String currencyName);

}
