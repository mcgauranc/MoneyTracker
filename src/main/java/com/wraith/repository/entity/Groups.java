package com.wraith.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User: rowan.massey
 * Date: 12/08/12
 * Time: 20:53
 */
@Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "group_id"))})
public class Groups extends BaseEntity implements Serializable {

    private String name;
    private Set<Authorities> authorities = new HashSet<>();

    @Column(name = "group_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Group_Authorities", joinColumns = {@JoinColumn(name = "group_authorities_group_id", referencedColumnName = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_authorities_authorities_id", referencedColumnName = "authorities_id")})
    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }
}
