package com.wraith.money.repository;

import com.wraith.money.data.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 22:56
 */
@RepositoryRestResource
public interface AccountTypeRepository extends MongoRepository<AccountType, Long> {

    public Page<AccountType> findByName(String accountTypeName, Pageable pageable);
}
