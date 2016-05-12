package com.sample.ormlite.uihandler.interactor;

/**
 * Created by praween on 25/4/16.
 */
public interface OnErrorListener {
    void onDatabaseError(int errorType, String error);
}
