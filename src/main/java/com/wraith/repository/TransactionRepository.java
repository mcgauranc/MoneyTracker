package com.wraith.repository;

import com.wraith.repository.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 15:48
 */
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
