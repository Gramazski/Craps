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
            "amount, win_amount, avatar, is_admin, name, surname, is_banned, sex, birthday FROM user";
    private static final String SQL_INSERT_USER = "INSERT INTO user(id, username, email, password, create_time," +
            "amount, win_amount, avatar, is_admin, name, surname, is_banned, sex, birthday)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT id, username, email, password, create_time," +
            "amount, win_amount, avatar, is_admin, name, surname, is_banned, sex, birthday FROM user " +
            "WHERE username = ?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET id=?, username=?, email=?, " +
            "create_time=?, amount=?, win_amount=?, avatar=?, is_admin=?, name=?, surname=?, is_banned=?," +
            "sex=?, birthday=? WHERE id=?";
    private static final String SQL_GET_ID_BY_USERNAME = "SELECT id FROM user WHERE username=?";
    private static final String SQL_GET_USERNAME_BY_ID = "SELECT username FROM user WHERE id=?";
    private static final String SQL_GET_ID_BY_EMAIL = "SELECT id FROM user WHERE email=?";

    public UserDAO(){
        connection = new WrapperConnection();
    }

    /**
     * @param name
     * @return
     * @throws DAOException
     */
    public User findEntityByName(String name) throws DAOException {
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_SELECT_USER_BY_NAME);
            st.setString(1, name);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                User user = new User();
                user.setPassword(resultSet.getString("password"));
                user.setUserName(resultSet.getString("username"));
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setAmount(resultSet.getInt("amount"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setCreateTime(resultSet.getString("create_time"));
                user.setWinAmount(resultSet.getInt("win_amount"));
                user.setBanned(resultSet.getBoolean("is_banned"));
                user.setSex(User.Sex.valueOf(resultSet.getString("sex")));
                user.setBirthday(resultSet.getString("birthday"));

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

    /**
     * @return
     * @throws DAOException
     */
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<User>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setPassword(resultSet.getString("password"));
                user.setUserName(resultSet.getString("username"));
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setAmount(resultSet.getInt("amount"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setCreateTime(resultSet.getString("create_time"));
                user.setWinAmount(resultSet.getInt("win_amount"));
                user.setBanned(resultSet.getBoolean("is_banned"));
                user.setSex(User.Sex.valueOf(resultSet.getString("sex")));
                user.setBirthday(resultSet.getString("birthday"));
                if (!user.isAdmin()){
                    users.add(user);
                }
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return users;
    }

    /**
     * @param entity
     * @throws DAOException
     */
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
            st.setBoolean(12, entity.isBanned());
            st.setString(13, entity.getSex().toString());
            st.setString(14, entity.getBirthday());
            st.executeUpdate();
            entity.setId(getUserIdByName(entity.getUserName()));
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    /**
     * @param entity
     * @return
     * @throws DAOException
     */
    public User update(User entity) throws DAOException {
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_UPDATE_USER);
            st.setInt(1, entity.getId());
            st.setString(2, entity.getUserName());
            st.setString(3, entity.getEmail());
            //st.setString(4, entity.getPassword());
            st.setString(4, entity.getCreateTime());
            st.setInt(5, entity.getAmount());
            st.setInt(6, entity.getWinAmount());
            st.setString(7, entity.getAvatar());
            st.setBoolean(8, entity.isAdmin());
            st.setString(9, entity.getName());
            st.setString(10, entity.getSurname());
            st.setBoolean(11, entity.isBanned());
            st.setString(12, entity.getSex().toString());
            st.setString(13, entity.getBirthday());
            st.setInt(14, entity.getId());
            st.executeUpdate();
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return entity;
    }

    /**
     * @param username
     * @return
     * @throws DAOException
     */
    public int getUserIdByName(String username) throws DAOException{
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_GET_ID_BY_USERNAME);
            st.setString(1, username);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("id");
            }
            else {
                return -1;
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    /**
     * @param id
     * @return
     * @throws DAOException
     */
    public String getUserNameById(int id) throws DAOException{
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_GET_USERNAME_BY_ID);
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("username");
            }
            else {
                return "";
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    /**
     * @param email
     * @return
     * @throws DAOException
     */
    public boolean isEmailExists(String email) throws DAOException{
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_GET_ID_BY_EMAIL);
            st.setString(1, email);
            ResultSet resultSet = st.executeQuery();

            return resultSet.next();
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    /**
     * @param userName
     * @return
     * @throws DAOException
     */
    public boolean isUserNameExists(String userName) throws DAOException{
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_GET_ID_BY_USERNAME);
            st.setString(1, userName);
            ResultSet resultSet = st.executeQuery();

            return resultSet.next();
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    public void close() {
        connection.close();
    }
}
