package com.sample.ormlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sample.ormlite.model.UserDataModel;
import com.sample.ormlite.ui.activities.AbstractBaseActivity;

import java.sql.SQLException;

public class AppDatabase extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    private static AppDatabase sInstance;

    /**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     */
    private Dao<UserDataModel, Long> mUserDao;
    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            /**
             * creates the UserDataModel database table
             */
            TableUtils.createTable(connectionSource, UserDataModel.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, UserDataModel.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an instance of the data access object
     *
     * @return
     * @throws SQLException
     */
    public Dao<UserDataModel, Long> getUserRuntimeDao() throws SQLException {
        if (mUserDao == null) {
            mUserDao = getDao(UserDataModel.class);
        }
        return mUserDao;
    }

    public static synchronized AppDatabase getInstance(Context context) {
        if(sInstance==null){
            sInstance = OpenHelperManager.getHelper(context,AppDatabase.class);
        }
        return sInstance;
    }
}