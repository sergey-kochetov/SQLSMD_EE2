package com.juja.sqlcmd_ee2.service;

import com.juja.sqlcmd_ee2.dao.entity.UserAction;
import org.springframework.stereotype.Component;
import com.juja.sqlcmd_ee2.dao.databasemanager.DatabaseManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public interface Service {

    List<String> commandList();

    DatabaseManager connect(String database, String user, String password)
            throws ServiceException;

    Set<String> getTableNames(DatabaseManager manager);

    List<List<String>> getTableData(DatabaseManager manager, String tableName);

    void createTable(DatabaseManager manager, String tableName, String keyName,
                     Map<String, Object> columnParameters);

    void createRecord(DatabaseManager manager, String tableName,
                      Map<String, Object> columnData);

    void updateRecord(DatabaseManager manager, String tableName,
                      String keyName, String keyValue,
                      Map<String, Object> columnData);

    void deleteRecord(DatabaseManager manager, String tableName,
                      String keyName, String keyValue);

    void clearTable(DatabaseManager manager, String tableName);

    void dropTable(DatabaseManager manager, String tableName);

    void createBase(DatabaseManager manager, String database);

    void dropBase(DatabaseManager manager, String database);

    List<UserAction> getAllFor(String userName);
}
