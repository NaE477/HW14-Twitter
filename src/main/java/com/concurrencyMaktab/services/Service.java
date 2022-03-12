package com.concurrencyMaktab.services;

import java.sql.Connection;

public abstract class Service<T> {
    private final Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Service(Connection connection) {
        this.connection = connection;
    }
}
