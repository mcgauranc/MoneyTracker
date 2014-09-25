package com.wraith.money.repository;

import com.wraith.money.data.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:36
 */
@RestResource
public interface AddressRepository extends CrudRepository<Address, Long> {
}
