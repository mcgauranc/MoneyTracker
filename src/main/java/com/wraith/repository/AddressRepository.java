package com.wraith.repository;

import com.wraith.repository.entity.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:36
 */
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
