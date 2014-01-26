package com.wraith.repository;

import com.wraith.repository.entity.Address;
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
