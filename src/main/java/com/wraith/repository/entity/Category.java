package com.wraith.repository.entity;


import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 02/09/12
 * Time: 14:05
 */
@Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "category_id"))})
public class Category extends BaseEntity implements Serializable {

    private Category parentCategory;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_parent_id")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @NaturalId
    @Column(name = "category_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
