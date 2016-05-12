package com.sample.ormlite.ui.activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sample.ormlite.R;
import com.sample.ormlite.constants.AppConstants;
import com.sample.ormlite.controller.EventController;
import com.sample.ormlite.database.DatabaseColumnName;
import com.sample.ormlite.database.DbOperationConstants;
import com.sample.ormlite.listeners.OnAdapterActionListener;
import com.sample.ormlite.listeners.OnFilterActionListener;
import com.sample.ormlite.model.BaseDataModel;
import com.sample.ormlite.model.UserDataModel;
import com.sample.ormlite.ui.adapter.UserListAdapter;
import com.sample.ormlite.helper.DialogHelper;
import com.sample.ormlite.uihandler.implementer.UserListIntractorImp;
import com.sample.ormlite.uihandler.interactor.UserListIntractor;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AbstractBaseActivity implements OnAdapterActionListener, OnFilterActionListener, UserListIntractor.OnUserListListener, UserListIntractor.OnDeleteUserListener {
    private static final int INTENT_ADD_USER = 11;
    private static final int INTENT_UPDATE_USER = 12;
    private RecyclerView mUserListRV;
    private RecyclerView.Adapter mUserListAdapter;
    private List<UserDataModel> mUserDataModelList;
    private UserListIntractorImp mUserListIntractorImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        super.init();
    }

    @Override
    protected void initViews() {
        mUserListRV = (RecyclerView) findViewById(R.id.rv_user_list);
        findViewById(R.id.fab_add_user).setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mUserListIntractorImp = new UserListIntractorImp(mAppDatabase);
        mUserDataModelList = new ArrayList<UserDataModel>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mUserListRV.setLayoutManager(layoutManager);
        mUserListAdapter = new UserListAdapter(this, mUserDataModelList);
        mUserListRV.setAdapter(mUserListAdapter);
        mUserListIntractorImp.getUserList(null, null, null, null, false, 0, 10, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() == 0) {
                    mUserListIntractorImp.getUserList(null, null, null, null, false, 0, 10, UserListActivity.this);
                } else {
                    mUserListIntractorImp.getUserList(null, new String[]{DatabaseColumnName.COLUMN_USER_NAME}, new String[]{query}, DatabaseColumnName.COLUMN_ID, true, 0, 10, UserListActivity.this);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() == 0) {
                    mUserListIntractorImp.getUserList(null, null, null, null, false, 0, 10, UserListActivity.this);
                } else {
                    mUserListIntractorImp.getUserList(null, new String[]{DatabaseColumnName.COLUMN_USER_NAME}, new String[]{query}, DatabaseColumnName.COLUMN_ID, true, 0, 10, UserListActivity.this);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_filter) {
            Dialog filterDialog = DialogHelper.getFilterDialog(this, this);
            filterDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_user:
                EventController.with(this)
                        .start(AddUserActivity.class)
                        .requestCode(INTENT_ADD_USER)
                        .startActivityForResult()
                        .activity(this)
                        .execute();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_ADD_USER && resultCode == RESULT_OK) {
            mUserListIntractorImp.getUserList(null, null, null, null, false, 0, 10, this);
        } else if (requestCode == INTENT_UPDATE_USER && resultCode == RESULT_OK) {
            mUserListIntractorImp.getUserList(null, null, null, null, false, 0, 10, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFilterAction(int what) {
        switch (what) {
            case AppConstants.FILTER_LIMIT:
                mUserListIntractorImp.getUserList(null, null, null, null, false, 0, 5, this);
                break;
            case AppConstants.FILTER_ORDER_BY_NAME:
                mUserListIntractorImp.getUserList(null, null, null, DatabaseColumnName.COLUMN_USER_NAME, true, 0, 5, this);
                break;
            case AppConstants.FILTER_ORDER_BY_TIME:
                mUserListIntractorImp.getUserList(null, null, null, DatabaseColumnName.COLUMN_CREATED_BY, true, 0, 5, this);
                break;
        }
    }

    @Override
    public void onUserListSuccess(List<UserDataModel> userDataModels) {
        mUserDataModelList.clear();
        mUserDataModelList.addAll(userDataModels);
        mUserListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteUserSuccess(int position, BaseDataModel baseDataModel) {
        mUserDataModelList.remove(position);
        mUserListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDatabaseError(int errorType, String error) {
        switch (errorType) {
            case DbOperationConstants.ErrorCode.ERROR_SQL_EXCEPTION:
                Snackbar.make(null, error, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteAction(int position, String[] where, String[] values) {
        mUserListIntractorImp.deleteUser(position, where, values, this);
    }

    @Override
    public void onUpdateAction(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_EXTRA_NAME, mUserDataModelList.get(position).getUserName());
        bundle.putString(AppConstants.INTENT_EXTRA_EMAIL, mUserDataModelList.get(position).getEmail());
        bundle.putString(AppConstants.INTENT_EXTRA_WHERE, String.valueOf(mUserDataModelList.get(position).getId()));
        EventController.with(this)
                .start(AddUserActivity.class)
                .data(bundle)
                .requestCode(INTENT_UPDATE_USER)
                .startActivityForResult()
                .activity(this)
                .execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserListIntractorImp.destroy();
    }
}
