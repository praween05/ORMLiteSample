package com.sample.ormlite.model;

/**
 * Created by praween on 22/4/16.
 */
public class BaseDataModel {
    private int mErrorCode;
    private String mErrorString;

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }

    public String getErrorString() {
        return mErrorString;
    }

    public void setErrorString(String errorString) {
        mErrorString = errorString;
    }
}
