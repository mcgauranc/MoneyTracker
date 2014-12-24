package com.wraith.money.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * This class maps a given payee description from an upload, to a specific payee in the payee table. Should be used exclusively
 * for data uploads.
 * <p/>
 * User: rowan.massey
 * Date: 09/09/2014
 * Time: 20:40
 */
//@Entity
@Document
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "payeemapping_id")),
        @AttributeOverride(name = "version", column = @Column(name = "payeemapping_version"))})
public class PayeeMapping extends BaseEntity implements Serializable {

    @DBRef
    private Payee payee;
    private String payeeDescription;

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public String getPayeeDescription() {
        return payeeDescription;
    }

    public void setPayeeDescription(String payeeDescription) {
        this.payeeDescription = payeeDescription;
    }
}
