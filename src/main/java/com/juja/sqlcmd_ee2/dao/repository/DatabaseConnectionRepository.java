package com.juja.sqlcmd_ee2.dao.repository;

import org.springframework.data.repository.CrudRepository;
import com.juja.sqlcmd_ee2.dao.entity.DatabaseConnection;

public interface DatabaseConnectionRepository extends CrudRepository<DatabaseConnection, Integer> {

    DatabaseConnection findByUserNameAndDbName(String username, String dbName);
}
