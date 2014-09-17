package com.wraith.money.repository;

import com.wraith.money.data.AccountType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 22:56
 */
public interface AccountTypeRepository extends CrudRepository<AccountType, Long> {

    public List<AccountType> findByName(String accountTypeName);
}
