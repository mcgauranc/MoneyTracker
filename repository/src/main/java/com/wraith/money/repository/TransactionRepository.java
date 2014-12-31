package com.wraith.money.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.wraith.money.data.Transaction;

/**
 * User: rowan.massey
 * Date: 24/02/13
 */
@RepositoryRestResource
public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
