package com.wraith.repository.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 23/07/12
 * Time: 20:56
 */
@MappedSuperclass
public class BaseEntity implements Cloneable, Serializable {

    protected long id;
    protected long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Version
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
