package com.wraith.money.data;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 17/08/12
 */
@Document
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "country_id")),
        @AttributeOverride(name = "version", column = @Column(name = "country_version"))})
public class Country extends BaseEntity implements Serializable {

    private String name;
    private String iso;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }
}
