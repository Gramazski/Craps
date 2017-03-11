package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gs on 19.02.2017.
 */
public class UserDAO extends AbstractDAO<User> {
    private WrapperConnection connection;
    private static final String SQL_SELECT_ALL_USERS = "SELECT id, name FROM user";
    private static final String SQL_INSERT_USER = "INSERT INTO user(id, name) VALUES(?,?)";
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT id, name, password FROM user WHERE name = ?";

    public UserDAO(){
        connection = new WrapperConnection();
    }

    public User findEntityByName(String name) throws DAOException {
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_SELECT_USER_BY_NAME);
            st.setString(1, name);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                User user = new User(/*resultSet.getInt("id")*/);
                user.setPassword(resultSet.getString("password"));
                user.setUserName(resultSet.getString("name"));

                return user;
            }
            else {
                return null;
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<User>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = new User(/*resultSet.getInt("id")*/);
                user.setUserName(resultSet.getString("name"));
                users.add(user);
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return users;
    }

    public User findEntityById(int id) {
        return null;
    }

    public boolean delete(int id) {
        return false;
    }

    public boolean delete(User entity) {
        return false;
    }

    public void create(User entity) throws DAOException {
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_INSERT_USER);
            st.setString(1, null);
            st.setString(2, entity.getUserName());
            st.executeUpdate();
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

    }

    public User update(User entity) {
        return null;
    }

    public void close() {
        connection.close();
    }
}
