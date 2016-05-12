package com.sample.ormlite.uihandler.interactor;

import com.sample.ormlite.model.BaseDataModel;
import com.sample.ormlite.model.UserDataModel;

import java.util.List;

/**
 * Created by praween on 22/4/16.
 */
public interface UserListIntractor {

    interface OnUserListListener extends OnErrorListener {
        void onUserListSuccess(List<UserDataModel> userDataModels);
    }

    interface OnDeleteUserListener extends OnErrorListener {
        void onDeleteUserSuccess(int position,BaseDataModel baseDataModel);
    }

    void getUserList(Iterable<String> projection, String[] where, String[] values, String orderBy, boolean ascending,long offset,long limit,OnUserListListener onUserListListener);
    void deleteUser(int position,String[]where,String[]values,OnDeleteUserListener onDeleteUserListener);
    void destroy();
}
