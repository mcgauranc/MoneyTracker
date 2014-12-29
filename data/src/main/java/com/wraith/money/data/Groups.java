package com.wraith.money.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is a representation of the groups entity, it manages all of the groups that the relevant user belongs to.
 *
 * User: rowan.massey
 * Date: 12/08/12
 */
@Document
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "group_id")),
        @AttributeOverride(name = "version", column = @Column(name = "group_version"))})
public class Groups extends BaseEntity implements Serializable {

    private String name;
    @DBRef
    private Set<Authorities> authorities = new HashSet<>();

    public Groups() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }
}
