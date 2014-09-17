package com.wraith.money.dataupload.exception;

/**
 * User: rowan.massey
 * Date: 17/09/2014
 * Time: 20:08
 */
public class DataUploadException extends RuntimeException {

    public DataUploadException(String errorMessage) {
        super(errorMessage);
    }

    public DataUploadException(Exception e) {
        super(e);
    }
}
