package com.wraith.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: rowan.massey
 * Date: 09/09/2014
 * Time: 21:17
 */
@Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "dataupload_id")),
        @AttributeOverride(name = "version", column = @Column(name = "dataupload_version"))})
public class DataUpload extends BaseEntity implements Serializable {

    private String description;
    private Date uploadDate;
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
