package com.wraith.repository.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 12/08/12
 * Time: 20:51
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "authorities_id")),
        @AttributeOverride(name = "version", column = @Column(name = "authorities_version"))})
public class Authorities extends BaseEntity implements Serializable {

    private String authority;

    @Column(name = "authorities_authority", nullable = false)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}