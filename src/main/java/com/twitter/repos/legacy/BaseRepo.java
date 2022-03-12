package com.twitter.repos.legacy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public abstract class BaseRepo<T> implements Repo<T>{
    private final Connection connection;

    public BaseRepo(Connection connection){
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    protected abstract T mapTo(ResultSet rs);

    protected abstract List<T> mapToList(ResultSet rs);
}
