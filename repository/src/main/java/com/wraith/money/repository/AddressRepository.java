package com.wraith.money.repository;

import com.wraith.money.data.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:36
 */
@RepositoryRestResource
public interface AddressRepository extends MongoRepository<Address, Long> {
}
