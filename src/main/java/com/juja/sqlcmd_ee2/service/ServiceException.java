package com.juja.sqlcmd_ee2.service;

public class ServiceException extends Exception {

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
