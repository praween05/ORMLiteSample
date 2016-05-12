package com.sample.ormlite.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by praween on 2/5/16.
 */
public class RequestHandler {
    private final EventController mAppController;
    private final AppRequest.Builder mAppRequestBuilder;
    RequestHandler(EventController appController, int requestCode, Class targetClass) {
        if (appController.shutdown) {
            throw new IllegalStateException(
                    "EventController instance already shut down. Cannot submit new requests.");
        }
        mAppController = appController;
        this.mAppRequestBuilder = new AppRequest.Builder(targetClass);
    }

    public RequestHandler requestCode(int requestCode) {
        mAppRequestBuilder.setRequestCode(requestCode);
        return this;
    }

    public RequestHandler open(Class targetClass) {
        mAppRequestBuilder.setTargetClass(targetClass);
        return this;
    }

    public RequestHandler data(Bundle bundle) {
        mAppRequestBuilder.setData(bundle);
        return this;
    }

    public RequestHandler service() {
        mAppRequestBuilder.setService();
        return this;
    }

    public RequestHandler startActivityForResult() {
        mAppRequestBuilder.setStartActivityForResult(true);
        return this;
    }

    public RequestHandler startActivity() {
        mAppRequestBuilder.setStartActivityForResult(false);
        return this;
    }

    public RequestHandler activity(Activity activity) {
        mAppRequestBuilder.setActivity(activity);
        return this;
    }

    public void execute() {
        AppRequest appRequest =mAppRequestBuilder.build();
        Intent intent = new Intent(appRequest.mActivity,appRequest.mTargetClass);
        if(mAppRequestBuilder.hasData()){
            intent.putExtras(appRequest.mExtras);
        }
        if(appRequest.startActivityForResult){
            appRequest.mActivity.startActivityForResult(intent,appRequest.mRequestCode);
        }
        if(appRequest.isService){
            appRequest.mActivity.startService(intent);
        }
    }
}
