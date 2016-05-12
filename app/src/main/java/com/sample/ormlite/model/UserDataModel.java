package com.sample.ormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sample.ormlite.database.DatabaseColumnName;

import java.util.Date;

@DatabaseTable
public class UserDataModel {

    @DatabaseField(columnName = DatabaseColumnName.COLUMN_ID,generatedId = true)
    private Long mId;

    @DatabaseField(columnName = DatabaseColumnName.COLUMN_USER_NAME)
    private String mUserName;

    @DatabaseField(unique = true,columnName = DatabaseColumnName.COLUMN_USER_EMAIL)
    private String mEmail;

    @DatabaseField(columnName = DatabaseColumnName.COLUMN_CREATED_BY,canBeNull = true)
    private Date mDateCreated;


    public UserDataModel(){

    }
    public UserDataModel(String userName, String description, Date dateCreated) {
        this.mUserName = userName;
        this.mEmail = description;
        this.mDateCreated = dateCreated;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        this.mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.mDateCreated = dateCreated;
    }

}