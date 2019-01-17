package com.juja.sqlcmd_ee2.dao.databasemanager;

public class DatabaseException extends Exception {

    public DatabaseException(String message, Exception e){
       super(message, e);
    }
}
