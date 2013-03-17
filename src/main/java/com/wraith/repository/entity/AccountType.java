package com.wraith.repository.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 15/08/12
 * Time: 22:11
 */
@Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "acctype_id"))})
public class AccountType extends BaseEntity implements Serializable {
    private String name;

    @Column(name = "acctype_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
