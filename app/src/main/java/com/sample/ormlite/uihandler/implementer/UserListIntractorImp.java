package com.sample.ormlite.uihandler.implementer;

import com.sample.ormlite.dataaccesslayer.DatabaseOperations;
import com.sample.ormlite.database.AppDatabase;
import com.sample.ormlite.database.DbOperationConstants;
import com.sample.ormlite.model.BaseDataModel;
import com.sample.ormlite.model.UserDataParentModel;
import com.sample.ormlite.uihandler.interactor.UserListIntractor;

/**
 * Created by praween on 21/4/16.
 */
public class UserListIntractorImp implements UserListIntractor {

    private AppDatabase mAppDatabase;
    public UserListIntractorImp(AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
    }

    @Override
    public void getUserList(Iterable<String> projection, String[] where, String[] values, String orderBy, boolean ascending,long offset,long limit, OnUserListListener onUserListListener) {
        UserDataParentModel userDataParentModel = DatabaseOperations.fetch(mAppDatabase,projection,where,values,orderBy,ascending,offset,limit);
        if (userDataParentModel.getErrorCode()== DbOperationConstants.ErrorCode.SUCCESS){
            onUserListListener.onUserListSuccess(userDataParentModel.getUserDataModelList());
        }else{
            onUserListListener.onDatabaseError(userDataParentModel.getErrorCode(),userDataParentModel.getErrorString());
        }
    }

    @Override
    public void deleteUser(int position,String[] where, String[] values, OnDeleteUserListener onDeleteUserListener) {
        BaseDataModel baseDataModel = DatabaseOperations.delete(mAppDatabase,where,values);
        if(baseDataModel.getErrorCode()== DbOperationConstants.ErrorCode.SUCCESS){
            onDeleteUserListener.onDeleteUserSuccess(position,baseDataModel);
        }else{
            onDeleteUserListener.onDatabaseError(baseDataModel.getErrorCode(),baseDataModel.getErrorString());
        }
    }

    @Override
    public void destroy() {
        if (mAppDatabase.isOpen()){
            mAppDatabase.close();
        }
    }


}
