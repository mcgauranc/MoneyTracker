package com.wraith.money.data;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 02/09/12
 * Time: 14:05
 */
//@Entity
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "category_id")),
        @AttributeOverride(name = "version", column = @Column(name = "category_version"))})
public class Category extends BaseEntity implements Serializable {

    @DBRef
    private Category parentCategory;
    private String name;

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
