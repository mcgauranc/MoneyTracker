package com.wraith.money.data;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the mappings of columns to xls (or csv) columns for a data upload.
 *
 * User: rowan.massey Date: 09/09/2014
 */
@Document
public class DataUploadMapping extends BaseEntity implements Serializable {

	@DBRef
	private DataUpload dataUpload;
	private String entityName;
	private String localField;
	private String importedField;
	private String dataType;

	public DataUpload getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(DataUpload dataUpload) {
		this.dataUpload = dataUpload;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getLocalField() {
		return localField;
	}

	public void setLocalField(String localField) {
		this.localField = localField;
	}

	public String getImportedField() {
		return importedField;
	}

	public void setImportedField(String importedField) {
		this.importedField = importedField;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
