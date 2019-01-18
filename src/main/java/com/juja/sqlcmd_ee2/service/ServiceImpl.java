package com.juja.sqlcmd_ee2.service;

import com.juja.sqlcmd_ee2.dao.entity.UserAction;
import com.juja.sqlcmd_ee2.dao.repository.UserActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.juja.sqlcmd_ee2.dao.databasemanager.DatabaseException;
import com.juja.sqlcmd_ee2.dao.databasemanager.DatabaseManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Transactional
public abstract class ServiceImpl implements Service {

    private List<String> commands;

    public abstract DatabaseManager getManager();

    @Autowired
    private UserActionRepository userAction;

    @Override
    public List<String> commandList() {
        return commands;
    }

    @Override
    public DatabaseManager connect(String database, String user, String password)
            throws ServiceException {
        DatabaseManager manager = getManager();
        try {
            manager.connect(database, user, password);
            userAction.saveAction("CONNECT", user, database);
            return manager;
        } catch (DatabaseException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Set<String> getTableNames(DatabaseManager manager) {
        userAction.saveAction("GET TABLES LIST",
                manager.getUser(), manager.getDatabase());
        return manager.getTableNames();
    }

    @Override
    public List<List<String>> getTableData(DatabaseManager manager, String tableName) {
        List<List<String>> tableData = manager.getTableData(tableName);
        List<String> columnNames = manager.getColumnNames(tableName);
        tableData.add(0, columnNames);
        userAction.saveAction(String.format("GET TABLE ( %s )", tableName),
                manager.getUser(), manager.getDatabase());
        return tableData;
    }

    @Override
    public void createTable(DatabaseManager manager, String tableName, String keyName,
                            Map<String, Object> columnParameters) {
        manager.createTable(tableName, keyName, columnParameters);
        userAction.saveAction(String.format("CREATE TABLE ( %s )", tableName),
                manager.getUser(), manager.getDatabase());
    }

    @Override
    public void createRecord(DatabaseManager manager, String tableName,
                             Map<String, Object> columnData) {
        manager.createRecord(tableName, columnData);
        userAction.saveAction(String.format("CREATE RECORD IN TABLE ( %s )", tableName),
                manager.getUser(), manager.getDatabase());
    }

    @Override
    public void updateRecord(DatabaseManager manager, String tableName,
                             String keyName, String keyValue,
                             Map<String, Object> columnData) {
        manager.updateRecord(tableName, keyName, keyValue, columnData);
        userAction.saveAction(String.format("UPDATE RECORD IN TABLE ( %s ) KEY = %s", tableName, keyValue),
                manager.getUser(), manager.getDatabase());
    }

    @Override
    public void deleteRecord(DatabaseManager manager, String tableName,
                             String keyName, String keyValue) {
        manager.deleteRecord(tableName, keyName, keyValue);
        userAction.saveAction(String.format("DELETE RECORD IN TABLE ( %s ) KEY = %s", tableName, keyValue),
                manager.getUser(), manager.getDatabase());
    }

    @Override
    public void clearTable(DatabaseManager manager, String tableName) {
        manager.clearTable(tableName);
        userAction.saveAction(String.format("CLEAR TABLE ( %s )", tableName),
                manager.getUser(), manager.getDatabase());
    }

    @Override
    public void dropTable(DatabaseManager manager, String tableName) {
        manager.dropTable(tableName);
        userAction.saveAction(String.format("DELETE TABLE ( %s )", tableName),
                manager.getUser(), manager.getDatabase());
    }

    @Override
    public void createBase(DatabaseManager manager, String database) {
        manager.createBase(database);
        userAction.saveAction(String.format("CREATE DATABASE ( %s )", database),
                manager.getUser(), manager.getDatabase());
    }

    @Override
    public void dropBase(DatabaseManager manager, String database) {
        manager.dropBase(database);
        userAction.saveAction(String.format("DELETE DATABASE ( %s )", database),
                manager.getUser(), manager.getDatabase());
    }

    @Override
    public List<UserAction> getAllFor(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User name cant be null");
        }
        return userAction.findByUserName(userName);
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
