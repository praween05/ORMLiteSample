package com.sample.ormlite.uihandler.implementer;

import com.sample.ormlite.dataaccesslayer.DatabaseOperations;
import com.sample.ormlite.database.AppDatabase;
import com.sample.ormlite.database.DbOperationConstants;
import com.sample.ormlite.model.BaseDataModel;
import com.sample.ormlite.model.UserDataModel;
import com.sample.ormlite.uihandler.interactor.AddUserInetractor;

import java.util.List;

/**
 * Created by praween on 21/4/16.
 */
public class AddUserIntractorImp implements AddUserInetractor {

    private AppDatabase mAppDatabase;
    public AddUserIntractorImp(AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
    }

    @Override
    public void destroy() {
        if (mAppDatabase.isOpen()){
            mAppDatabase.close();
        }
    }

    @Override
    public void addUser(UserDataModel userDataModel,OnAddUserFinishListener onAddUserFinishListener) {
        BaseDataModel baseDataModel = DatabaseOperations.insert(mAppDatabase,userDataModel);
        if(baseDataModel.getErrorCode()== DbOperationConstants.ErrorCode.SUCCESS){
            onAddUserFinishListener.onAddUserSuccess(userDataModel);
        }else{
            onAddUserFinishListener.onDatabaseError(baseDataModel.getErrorCode(),baseDataModel.getErrorString());
        }
    }

    @Override
    public void addMultipleUser(List<UserDataModel> userDataModelList, OnAddUserFinishListener onAddUserFinishListener) {
        BaseDataModel baseDataModel = DatabaseOperations.bulkInsert(mAppDatabase,userDataModelList);
        if(baseDataModel.getErrorCode()== DbOperationConstants.ErrorCode.SUCCESS){
            onAddUserFinishListener.onAddUserSuccess(userDataModelList.get(0));
        }else{
            onAddUserFinishListener.onDatabaseError(baseDataModel.getErrorCode(),baseDataModel.getErrorString());
        }

    }

    @Override
    public void updateUser(String[]columnNames,String[] columnValues,String[]where,String[] values,OnAddUserFinishListener onAddUserFinishListener) {
        BaseDataModel baseDataModel = DatabaseOperations.update(mAppDatabase,columnNames,columnValues,where,values);
        if(baseDataModel.getErrorCode()== DbOperationConstants.ErrorCode.SUCCESS){
            onAddUserFinishListener.onUpdateUserSuccess();
        }else{
            onAddUserFinishListener.onDatabaseError(baseDataModel.getErrorCode(),baseDataModel.getErrorString());
        }
    }
}
