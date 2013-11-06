package com.wraith.repository;

import com.wraith.repository.entity.PersistentLogin;
import org.springframework.data.repository.CrudRepository;

/**
 * User: rowan.massey
 * Date: 29/09/13
 * Time: 17:13
 */
public interface CustomTokenRepository extends CrudRepository<PersistentLogin, String> {
}
