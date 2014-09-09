package com.wraith.repository.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 09/09/2014
 * Time: 21:17
 */
@Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "datauploadmapping_id")),
        @AttributeOverride(name = "version", column = @Column(name = "datauploadmapping_version"))})
public class DataUploadMapping extends BaseEntity implements Serializable {


}
