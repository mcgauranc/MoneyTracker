package com.wraith.money.repository;

import com.wraith.money.data.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: rowan.massey
 * Date: 24/01/13
 * Time: 21:56
 */
@RepositoryRestResource
public interface AccountRepository extends MongoRepository<Account, Long> {

    public Page<Account> findByName(@Param("accountName") String accountName, Pageable pageable);

}
