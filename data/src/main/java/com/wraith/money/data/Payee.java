package com.wraith.money.data;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 16/08/12
 * Time: 22:04
 */
//@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Audited
//@AuditTable(value = "Payee_Audit")
@Document
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "payee_id")),
        @AttributeOverride(name = "version", column = @Column(name = "payee_version"))})
public class Payee extends BaseEntity implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
