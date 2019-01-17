package com.juja.sqlcmd_ee2.dao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.juja.sqlcmd_ee2.dao.entity.UserAction;

import java.util.List;

public interface UserActionRepository extends CrudRepository<UserAction, Integer>, UserActionRepositoryCustom {

    @Query(value = "SELECT ua FROM UserAction ua WHERE ua.connection.userName = :userName")
    List<UserAction> findByUserName(@Param("userName") String userName);
}
