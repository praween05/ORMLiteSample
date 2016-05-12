package com.sample.ormlite.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View.OnClickListener;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.sample.ormlite.R;
import com.sample.ormlite.database.AppDatabase;

/**
 * @author soni Base activity all the activity defined in this app will
 *         extend this class this is the base class for all activity
 */
public abstract class AbstractBaseActivity extends AppCompatActivity implements OnClickListener
{
    protected AppDatabase mAppDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    protected void init()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAppDatabase = AppDatabase.getInstance(this);
        initViews();
        initVariables();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    protected abstract void initViews();

    protected abstract void initVariables();


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_MENU||super.onKeyDown(keyCode, event);
    }
}
