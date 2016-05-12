package com.sample.ormlite.controller;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by praween on 2/5/16.
 */
public class AppRequest {
    public Bundle mExtras;
    public int mRequestCode;
    public Class mTargetClass;
    public boolean isService;
    public boolean startActivityForResult;
    public Activity mActivity;

    public AppRequest(Bundle extras, int requestCode, Class targetClass, boolean isService, boolean startActivityForResult, Activity activity) {
        mExtras = extras;
        mRequestCode = requestCode;
        mTargetClass = targetClass;
        this.isService = isService;
        this.startActivityForResult = startActivityForResult;
        mActivity = activity;
    }

    public static final class Builder {
        private Bundle mBundle;
        private int mRequestCode;
        private Class mTargetClass;
        private boolean mIsService;
        private boolean mStartActivityForResult;
        private Activity mActivity;

        public Builder(Bundle map) {
            setData(map);
        }

        public Builder(Class targetClass) {
            setTargetClass(targetClass);
        }

        public Builder(int requestCode) {
            setRequestCode(requestCode);
        }

        public Builder(AppRequest appRequest) {
            mBundle = appRequest.mExtras;
            mRequestCode = appRequest.mRequestCode;
            mTargetClass = appRequest.mTargetClass;
        }

        public void setActivity(Activity activity) {
            mActivity=activity;
        }

        public void setData(Bundle bundle) {
            mBundle = bundle;
        }

        public void setTargetClass(Class targetClass) {
            mTargetClass = targetClass;
        }

        public void setRequestCode(int requestCode) {
            mRequestCode = requestCode;
        }

        public void setService() {
            mIsService = true;
        }

        public void setStartActivityForResult(boolean isStartActivityForResult) {
            mStartActivityForResult = isStartActivityForResult;
        }

        public boolean hasData(){
            return mBundle !=null?true:false;
        }

        public boolean isService() {
            return mIsService;
        }

        public boolean isStartActivityForResult() {
            return mStartActivityForResult;
        }
        public boolean hasRequestCode(){
            return mRequestCode>0?true:false;
        }

        /** Create the immutable {@link AppRequest} object. */
        public AppRequest build() {
            if (isService() && isStartActivityForResult()) {
                throw new IllegalStateException("Service and activity open can not be applied at same time");
            }
            if (isStartActivityForResult() && !hasRequestCode()) {
                throw new IllegalStateException(
                        "Open activity for result can not be applied as request code not supplied or request code is -ve");
            }
            if(!isService() && (mActivity==null||!(mActivity instanceof Activity))){
                throw new IllegalStateException(
                        "Open activity can not be applied as open activity reference not provided");
            }
            return new AppRequest(mBundle,mRequestCode,mTargetClass,mIsService,mStartActivityForResult, mActivity);
        }
    }
}
