package com.wraith.money.data;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 12/08/12
 * Time: 20:51
 */
@Document
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "authorities_id")),
        @AttributeOverride(name = "version", column = @Column(name = "authorities_version"))})
public class Authorities extends BaseEntity implements Serializable {

    private String authority;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
