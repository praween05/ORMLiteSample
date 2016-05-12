package com.sample.ormlite.dataaccesslayer;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.sample.ormlite.database.AppDatabase;
import com.sample.ormlite.database.DbOperationConstants;
import com.sample.ormlite.model.BaseDataModel;
import com.sample.ormlite.model.UserDataModel;
import com.sample.ormlite.model.UserDataParentModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by praween on 15/4/16.
 */
public class DatabaseOperations {

    /**
     * get all user data from database
     * @param appDatabase
     * @param projection
     * @param where
     * @param values
     * @param orderBy
     * @param ascending
     * @return List<UserDataModel>
     */
    public static UserDataParentModel fetch(AppDatabase appDatabase, Iterable<String> projection, String[] where, String[] values, String orderBy, boolean ascending, long offset, long limit) {
        UserDataParentModel userDataParentModel = new UserDataParentModel();
        try {
            QueryBuilder<UserDataModel, Long> queryBuilder = appDatabase.getUserRuntimeDao().queryBuilder();
            if (projection != null) {
                queryBuilder.selectColumns(projection);
            }
            if (orderBy != null && orderBy.length() > 0) {
                queryBuilder.orderBy(orderBy, ascending);
            }
            if (limit != 0) {
                queryBuilder.offset(offset).limit(limit);
            }
            if (where == null) {
                userDataParentModel.setErrorCode(DbOperationConstants.ErrorCode.SUCCESS);
                userDataParentModel.setUserDataModelList(queryBuilder.query());

            } else if (where != null && where.length == values.length) {
                for (int i = 0; i < where.length; i++) {
                    queryBuilder.where().eq(where[i], values[i]);
                }
                userDataParentModel.setErrorCode(DbOperationConstants.ErrorCode.SUCCESS);
                userDataParentModel.setUserDataModelList(queryBuilder.query());
            } else {
                userDataParentModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_WRONG_WHERE_CLAUSE);
                userDataParentModel.setErrorString(DbOperationConstants.ErrorString.WRONG_WHERE_CLAUSE);
            }
        } catch (SQLException e) {
            userDataParentModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_SQL_EXCEPTION);
            userDataParentModel.setErrorString(DbOperationConstants.ErrorString.SQL_EXCEPTION);
            e.printStackTrace();
        }
        return userDataParentModel;
    }

    /**
     * use to insert object in database
     *
     * @param appDatabase
     * @param userDataModel
     * @return
     */
    public static BaseDataModel insert(AppDatabase appDatabase, UserDataModel userDataModel) {
        BaseDataModel baseDataModel = new BaseDataModel();
        try {
            appDatabase.getUserRuntimeDao().createIfNotExists(userDataModel);
            baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.SUCCESS);
        } catch (SQLException e) {
            baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_SQL_EXCEPTION);
            baseDataModel.setErrorString(DbOperationConstants.ErrorString.SQL_EXCEPTION);
            e.printStackTrace();
        }
        return baseDataModel;
    }

    /**
     * use to insert multiple object in one go
     *
     * @param appDatabase
     * @param userDataModelList
     * @return
     */
    public static BaseDataModel bulkInsert(AppDatabase appDatabase, List<UserDataModel> userDataModelList) {
        BaseDataModel baseDataModel = new BaseDataModel();
        try {
            for (UserDataModel userDataModel : userDataModelList) {
                appDatabase.getUserRuntimeDao().createIfNotExists(userDataModel);
            }
            baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.SUCCESS);
        } catch (SQLException e) {
            baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_SQL_EXCEPTION);
            baseDataModel.setErrorString(DbOperationConstants.ErrorString.SQL_EXCEPTION);
            e.printStackTrace();
        }
        return baseDataModel;
    }

    /**
     * use to delete data from database
     *
     * @param appDatabase
     * @param where
     * @param values
     * @return BaseDataModel
     */
    public static BaseDataModel delete(AppDatabase appDatabase, String[] where, String[] values) {
        BaseDataModel baseDataModel = new BaseDataModel();
        try {
            DeleteBuilder<UserDataModel, Long> deleteBuilder = appDatabase.getUserRuntimeDao().deleteBuilder();
            if (where != null && where.length == values.length) {
                for (int i = 0; i < where.length; i++) {
                    deleteBuilder.where().eq(where[i], values[i]);
                }
                int delete = deleteBuilder.delete();
                if (delete > 0) {
                    baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.SUCCESS);
                } else {
                    baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_WRONG_WHERE_CLAUSE);
                    baseDataModel.setErrorString(DbOperationConstants.ErrorString.WRONG_WHERE_CLAUSE);
                }
            } else {
                baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_WRONG_WHERE_CLAUSE);
                baseDataModel.setErrorString(DbOperationConstants.ErrorString.WRONG_WHERE_CLAUSE);
            }
        } catch (SQLException e) {
            baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_SQL_EXCEPTION);
            baseDataModel.setErrorString(DbOperationConstants.ErrorString.SQL_EXCEPTION);
            e.printStackTrace();
        }
        return baseDataModel;
    }

    /**
     * use to delete data from database
     *
     * @param appDatabase
     * @param columnNames
     * @param columnValues
     * @param where
     * @param values
     * @return BaseDataModel
     */
    public static BaseDataModel update(AppDatabase appDatabase, String[] columnNames, String[] columnValues, String[] where, String[] values) {
        BaseDataModel baseDataModel = new BaseDataModel();
        try {
            UpdateBuilder<UserDataModel, Long> updateBuilder = appDatabase.getUserRuntimeDao().updateBuilder();
            if (columnNames != null && columnNames.length == columnValues.length) {
                for (int i = 0; i < where.length; i++) {
                    updateBuilder.updateColumnValue(columnNames[i], columnValues[i]);
                }
                if (where != null && where.length == values.length) {
                    for (int i = 0; i < where.length; i++) {
                        updateBuilder.where().eq(where[i], values[i]);
                    }
                    int delete = updateBuilder.update();
                    if (delete > 0) {
                        baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.SUCCESS);
                    } else {
                        baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_WRONG_WHERE_CLAUSE);
                        baseDataModel.setErrorString(DbOperationConstants.ErrorString.WRONG_WHERE_CLAUSE);
                    }
                } else {
                    baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_WRONG_WHERE_CLAUSE);
                    baseDataModel.setErrorString(DbOperationConstants.ErrorString.WRONG_WHERE_CLAUSE);
                }
            } else {
                baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_NOTHING_TO_UPDATE);
                baseDataModel.setErrorString(DbOperationConstants.ErrorString.NO_DATA_FOR_UPDATE);
            }

        } catch (SQLException e) {
            baseDataModel.setErrorCode(DbOperationConstants.ErrorCode.ERROR_SQL_EXCEPTION);
            baseDataModel.setErrorString(DbOperationConstants.ErrorString.SQL_EXCEPTION);
            e.printStackTrace();
        }
        return baseDataModel;
    }
}
