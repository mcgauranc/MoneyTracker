package com.wraith.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: rowan.massey
 * Date: 23/07/12
 * Time: 20:56
 */
@MappedSuperclass
public class BaseEntity implements Cloneable, Serializable {

    private int id;
    private Date createdDate;
    private Date updatedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
