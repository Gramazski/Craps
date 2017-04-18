package com.gramazski.craps.connector;

import com.gramazski.craps.exception.DAOException;
import com.gramazski.craps.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by gs on 21.02.2017.
 */
public class WrapperConnection {
    private Connection connection;

    public WrapperConnection(){
        connection = ConnectionPool.getInstance().getConnection();
    }

    /**
     * @return
     * @throws DAOException
     */
    public Statement createStatement() throws DAOException {
        if (connection != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new DAOException("Can not create statement. Course: " + e.getMessage(), e);
            }

            if (statement != null) {
                return statement;
            }
        }

        throw new DAOException("Connection or statement is null");
    }

    /**
     * @param sqlRequest
     * @return
     * @throws DAOException
     */
    public PreparedStatement createPreparedStatement(String sqlRequest) throws DAOException{
        if (connection != null) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sqlRequest);
            } catch (SQLException e) {
                throw new DAOException("Can not create statement. Course: " + e.getMessage(), e);
            }

            if (statement != null) {
                return statement;
            }
        }

        throw new DAOException("Connection or statement is null");
    }

    /**
     * @param statement
     * @throws DAOException
     */
    public void closeStatement(Statement statement) throws DAOException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException("Can't close statement. Course: " + e.getMessage(), e);
            }
        }
    }

    public void close(){
        if (connection != null){
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
