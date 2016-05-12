package com.sample.ormlite.database;

/**
 * Created by praween on 22/4/16.
 */
public interface DbOperationConstants {
    interface ErrorCode {
        int SUCCESS = 200;
        int ERROR_WRONG_WHERE_CLAUSE = 1;
        int ERROR_SQL_EXCEPTION = 2;
        int ERROR_NOTHING_TO_UPDATE = 3;
    }

    interface ErrorString {
        String WRONG_WHERE_CLAUSE = "Something wrong in where clause";
        String SQL_EXCEPTION = "SQL Exception in querying for data";
        String NO_DATA_FOR_UPDATE = "No data for update table";
    }
}
