package com.wraith.money.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.wraith.money.data.DataUpload;

/**
 * This interface facilitates database access to the DataUpload table.
 * <p/>
 * User: rowan.massey
 * Date: 26/09/2014
 * Time: 21:48
 */
@RepositoryRestResource(itemResourceRel = "dataUpload", path = "/dataUploads")
public interface DataUploadRepository extends MongoRepository<DataUpload, String> {

}
