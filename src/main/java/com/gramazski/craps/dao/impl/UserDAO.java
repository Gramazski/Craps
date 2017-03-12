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
    private static final String SQL_SELECT_ALL_USERS = "SELECT id, username, email, password, create_time," +
            "amount, win_amount, avatar, is_admin, name, surname FROM user";
    private static final String SQL_INSERT_USER = "INSERT INTO user(id, username, email, password, create_time," +
            "amount, win_amount, avatar, is_admin, name, surname) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT id, username, email, password, create_time," +
            "amount, win_amount, avatar, is_admin, name, surname FROM user WHERE username = ?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET id=?, username=?, email=?, password=?," +
            " create_time=?, amount=?, win_amount=?, avatar=?, is_admin=?, name=?, surname=? WHERE id=?";

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
                User user = new User();
                user.setPassword(resultSet.getString("password"));
                user.setUserName(resultSet.getString("name"));
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setAmount(resultSet.getInt("amount"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setCreateTime(resultSet.getString("create_time"));
                user.setWinAmount(resultSet.getInt("win_amount"));

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
                User user = new User();
                user.setPassword(resultSet.getString("password"));
                user.setUserName(resultSet.getString("name"));
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setAmount(resultSet.getInt("amount"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setCreateTime(resultSet.getString("create_time"));
                user.setWinAmount(resultSet.getInt("win_amount"));
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
            st.setString(3, entity.getEmail());
            st.setString(4, entity.getPassword());
            st.setString(5, entity.getCreateTime());
            st.setInt(6, entity.getAmount());
            st.setInt(7, entity.getWinAmount());
            st.setString(8, entity.getAvatar());
            st.setBoolean(9, entity.isAdmin());
            st.setString(10, entity.getName());
            st.setString(11, entity.getSurname());
            st.executeUpdate();
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

    }

    public User update(User entity) throws DAOException {
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_UPDATE_USER);
            st.setString(1, null);
            st.setString(2, entity.getUserName());
            st.setString(3, entity.getEmail());
            st.setString(4, entity.getPassword());
            st.setString(5, entity.getCreateTime());
            st.setInt(6, entity.getAmount());
            st.setInt(7, entity.getWinAmount());
            st.setString(8, entity.getAvatar());
            st.setBoolean(9, entity.isAdmin());
            st.setString(10, entity.getName());
            st.setString(11, entity.getSurname());
            st.setInt(12, entity.getId());
            st.executeUpdate();
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return entity;
    }

    public void close() {
        connection.close();
    }
}
