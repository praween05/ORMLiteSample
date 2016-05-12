package com.sample.ormlite.model;

import java.util.List;

/**
 * Created by praween on 22/4/16.
 */
public class UserDataParentModel extends BaseDataModel {

    private List<UserDataModel> mUserDataModelList;

    public List<UserDataModel> getUserDataModelList() {
        return mUserDataModelList;
    }

    public void setUserDataModelList(List<UserDataModel> userDataModelList) {
        mUserDataModelList = userDataModelList;
    }
}
