package com.wraith.repository;

import com.wraith.repository.entity.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * User: rowan.massey
 * Date: 24/01/13
 * Time: 21:56
 */
public interface AccountRepository extends CrudRepository<Account, Long> {

}
