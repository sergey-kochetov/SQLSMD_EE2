package com.juja.sqlcmd_ee2.dao.repository;

public interface UserActionRepositoryCustom {

    void saveAction(String action, String user, String database);
}
