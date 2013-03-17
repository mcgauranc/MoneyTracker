package com.wraith.repository.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 12/08/12
 * Time: 20:51
 */
@Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "authorities_id"))})
public class Authorities extends BaseEntity implements Serializable {

    //    private User user;
    private String authority;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
//    @JoinColumn(name = "authorities_user_id", referencedColumnName = "user_id")
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    @Column(name = "authorities_authority", nullable = false)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
