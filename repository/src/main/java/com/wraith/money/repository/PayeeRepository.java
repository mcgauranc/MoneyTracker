package com.wraith.money.repository;

import com.wraith.money.data.Payee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 13:28
 */
@RepositoryRestResource
public interface PayeeRepository extends MongoRepository<Payee, Long> {

    public Page<Payee> findByName(@Param("payeeName") String payeeName, Pageable pageable);

}
