package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.Message;
import com.gramazski.craps.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gs on 13.03.2017.
 */
public class MessageDAO extends AbstractDAO<Message> {
    private WrapperConnection connection;
    private static final String SQL_SELECT_ALL_MESSAGES = "SELECT id, title, create_date, body, status, sender_id," +
            "receiver_id FROM message";
    private static final String SQL_SELECT_ALL_MESSAGES_FOR_USER = "SELECT id, title, create_date, body, status, sender_id," +
            "receiver_id FROM message WHERE sender_id=? OR receiver_id=?";
    private static final String SQL_INSERT_MESSAGE = "INSERT INTO message(id, title, create_date, body, status," +
            " sender_id, receiver_id) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_MESSAGE_BY_ID = "SELECT id, title, create_date, body, sender_id," +
            "status, receiver_id FROM message WHERE id = ?";
    private static final String SQL_UPDATE_MESSAGE = "UPDATE user SET id=?, title=?, create_date=?, body=?," +
            "status=?, sender_id=?, receiver_id=? WHERE id=?";
    private static final String SQL_GET_LAST_GENERATED_ID = "SELECT LAST_INSERT_ID()";

    public MessageDAO(){
        connection = new WrapperConnection();
    }

    @Override
    public List<Message> findAll() throws DAOException {
        List<Message> messages = new ArrayList<>();
        Statement st = null;
        try {
            st = connection.createStatement();
            UserDAO userDAO = new UserDAO();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_MESSAGES);
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt("id"));
                message.setBody(resultSet.getString("body"));
                message.setCreateDate(resultSet.getString("create_date"));
                message.setStatus(Message.Status.valueOf(resultSet.getString("status")));
                message.setReceiver(userDAO.getUserNameById(resultSet.getInt("receiver_id")));
                message.setSender(userDAO.getUserNameById(resultSet.getInt("sender_id")));
                message.setTitle(resultSet.getString("title"));
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return messages;
    }

    @Override
    public Message findEntityById(int id) {
        return null;
    }

    @Override
    public Message findEntityByName(String name) throws DAOException {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Message entity) {
        return false;
    }

    @Override
    public void create(Message entity) throws DAOException {
        PreparedStatement st = null;
        try(UserDAO userDAO = new UserDAO()) {
            st = connection.createPreparedStatement(SQL_INSERT_MESSAGE);
            st.setString(1, null);
            st.setString(2, entity.getTitle());
            st.setString(3, entity.getCreateDate());
            st.setString(4, entity.getBody());
            st.setString(5, entity.getStatus().toString());
            st.setInt(6, userDAO.getUserIdByName(entity.getSender()));
            st.setInt(7, userDAO.getUserIdByName(entity.getReceiver()));
            st.executeUpdate();
            entity.setId(getLastId());
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    @Override
    public Message update(Message entity) throws DAOException {
        PreparedStatement st = null;
        try(UserDAO userDAO = new UserDAO()) {
            st = connection.createPreparedStatement(SQL_UPDATE_MESSAGE);
            st.setInt(1, entity.getId());
            st.setString(2, entity.getTitle());
            st.setString(3, entity.getCreateDate());
            st.setString(4, entity.getBody());
            st.setString(5, entity.getStatus().toString());
            st.setInt(6, userDAO.getUserIdByName(entity.getSender()));
            st.setInt(7, userDAO.getUserIdByName(entity.getReceiver()));
            st.setInt(8, entity.getId());
            st.executeUpdate();

        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return entity;
    }

    @Override
    public void close() {
        connection.close();
    }

    public List<Message> getAllMessagesForUser(int id) throws DAOException{
        List<Message> messages = new ArrayList<>();
        PreparedStatement st = null;
        try(UserDAO userDAO = new UserDAO()) {
            st = connection.createPreparedStatement(SQL_SELECT_ALL_MESSAGES_FOR_USER);

            st.setInt(1, id);
            st.setInt(2, id);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt("id"));
                message.setBody(resultSet.getString("body"));
                message.setCreateDate(resultSet.getString("create_date"));
                message.setStatus(Message.Status.valueOf(resultSet.getString("status")));
                message.setReceiver(userDAO.getUserNameById(resultSet.getInt("receiver_id")));
                message.setSender(userDAO.getUserNameById(resultSet.getInt("sender_id")));
                message.setTitle(resultSet.getString("title"));

                messages.add(message);
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return messages;
    }

    private int getLastId() throws DAOException{
        Statement st = null;
        int id = -1;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_GET_LAST_GENERATED_ID);
            if (rs.next()){
                id = rs.getInt(1);
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return id;
    }
}
