package com.wraith.money.repository;

import com.wraith.money.data.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 15:48
 */
@RepositoryRestResource
public interface TransactionRepository extends MongoRepository<Transaction, Long> {
}
