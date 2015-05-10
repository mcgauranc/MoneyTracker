package com.wraith.money.repository.projection;

import com.wraith.money.data.entity.Address;
import com.wraith.money.data.entity.Users;
import org.springframework.data.rest.core.config.Projection;

/**
 * User: rowan.massey
 * Date: 08/03/2015
 * Time: 17:07
 */
@Projection(name = "inlineAddress", types = {Users.class})
public interface InLineAddress {
    Address getAddress();
}
