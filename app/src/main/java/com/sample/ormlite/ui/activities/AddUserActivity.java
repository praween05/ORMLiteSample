package com.sample.ormlite.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sample.ormlite.R;
import com.sample.ormlite.constants.AppConstants;
import com.sample.ormlite.database.DatabaseColumnName;
import com.sample.ormlite.database.DbOperationConstants;
import com.sample.ormlite.helper.AppValidationHelper;
import com.sample.ormlite.model.UserDataModel;
import com.sample.ormlite.ui.adapter.TestSpinnerAdapter;
import com.sample.ormlite.uihandler.implementer.AddUserIntractorImp;
import com.sample.ormlite.uihandler.interactor.AddUserInetractor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AddUserActivity extends AbstractBaseActivity implements AddUserInetractor.OnAddUserFinishListener, AddUserInetractor.OnUpdateUserFinishListener {

    private EditText mUserNameET, mEmailET;
    private TextInputLayout mNameIL, mEmailIL;
    private AddUserIntractorImp mAddUserIntractorImp;
    private String mWhereValues;
    private Button mActionBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        super.init();
    }

    @Override
    protected void initViews() {
        mNameIL = (TextInputLayout) findViewById(R.id.input_layout_name);
        mEmailIL = (TextInputLayout) findViewById(R.id.input_layout_email);
        mUserNameET = (EditText) findViewById(R.id.et_user_name);
        mEmailET = (EditText) findViewById(R.id.et_email);
        mActionBTN = (Button) findViewById(R.id.btn_add_user);
        mActionBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mAddUserIntractorImp = new AddUserIntractorImp(mAppDatabase);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String name = bundle.getString(AppConstants.INTENT_EXTRA_NAME);
            String email = bundle.getString(AppConstants.INTENT_EXTRA_EMAIL);
            mWhereValues = bundle.getString(AppConstants.INTENT_EXTRA_WHERE);
            if (mWhereValues != null) {
                mActionBTN.setText(R.string.action_edit_user);
            }
            if (name != null) {
                mUserNameET.setText(name);
            }
            if (email != null) {
                mEmailET.setText(email);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mAddUserIntractorImp.destroy();
    }


    @Override
    public void onAddUserSuccess(UserDataModel userDataModels) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onUpdateUserSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDatabaseError(int errorType, String error) {
        switch (errorType) {
            case DbOperationConstants.ErrorCode.ERROR_SQL_EXCEPTION:
                Snackbar.make(null, error, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_user:
                String name =mUserNameET.getText().toString().trim();
                String email = mEmailET.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    mNameIL.setErrorEnabled(true);
                    mNameIL.setError(getResources().getString(R.string.err_msg_name));
                }else if(!AppValidationHelper.isValidEmail(email)){
                    mEmailIL.setErrorEnabled(true);
                    mEmailIL.setError(getResources().getString(R.string.err_msg_name));
                }else{
                    mNameIL.setErrorEnabled(false);
                    mEmailIL.setErrorEnabled(false);
                    if (mWhereValues != null) {
                        mAddUserIntractorImp.updateUser(new String[]{DatabaseColumnName.COLUMN_USER_NAME, DatabaseColumnName.COLUMN_USER_EMAIL}
                                , new String[]{mUserNameET.getText().toString(), mEmailET.getText().toString()}
                                , new String[]{DatabaseColumnName.COLUMN_ID}
                                , new String[]{mWhereValues}
                                , this);
                    } else {
                        UserDataModel userDataModel = new UserDataModel(mUserNameET.getText().toString(), mEmailET.getText().toString(), new Date(System.currentTimeMillis()));
                        mAddUserIntractorImp.addUser(userDataModel, this);
                    }
                }

                break;
        }
    }
}
