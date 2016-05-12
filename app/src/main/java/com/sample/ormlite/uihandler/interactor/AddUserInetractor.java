package com.sample.ormlite.uihandler.interactor;

import com.sample.ormlite.model.UserDataModel;

import java.util.List;

/**
 * Created by praween on 22/4/16.
 */
public interface AddUserInetractor {

    interface OnAddUserFinishListener extends OnErrorListener {
        void onAddUserSuccess(UserDataModel userDataModels);
        void onUpdateUserSuccess();
    }

    interface OnUpdateUserFinishListener extends OnErrorListener {
        void onUpdateUserSuccess();
    }

    void addUser(UserDataModel userDataModel, OnAddUserFinishListener onAddUserFinishListener);
    void updateUser(String[]columnNames,String[]columnValues,String[]where,String[]values, OnAddUserFinishListener onAddUserFinishListener);
    void addMultipleUser(List<UserDataModel> userDataModelList, OnAddUserFinishListener onAddUserFinishListener);
    void destroy();
}
