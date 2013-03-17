package com.wraith.repository.entity;


import org.hibernate.annotations.NaturalId;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 17/08/12
 * Time: 15:35
 */
@Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "country_id"))})
public class Country extends BaseEntity implements Serializable {

    private String name;
    private String iso;

    @NaturalId
    @Column(name = "country_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "country_iso", nullable = false)
    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }
}
