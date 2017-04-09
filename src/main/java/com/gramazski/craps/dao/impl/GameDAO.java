package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GameDAO extends AbstractDAO<Game> {
    private WrapperConnection connection;
    private static final String SQL_SELECT_ALL_MESSAGES = "SELECT id, title, create_date, body, status, sender_id," +
            "receiver_id FROM message";
    private static final String SQL_SELECT_ALL_MESSAGES_FOR_USER = "SELECT id, title, create_date, body, status, sender_id," +
            "receiver_id FROM message WHERE sender_id=? OR receiver_id=?";
    private static final String SQL_INSERT_GAME = "INSERT INTO game(id, lobbyname, time VALUES(?,?,?)";
    private static final String SQL_SELECT_MESSAGE_BY_ID = "SELECT id, title, create_date, body, sender_id," +
            "status, receiver_id FROM message WHERE id = ?";
    private static final String SQL_UPDATE_MESSAGE = "UPDATE user SET id=?, title=?, create_date=?, body=?," +
            "status=?, sender_id=?, receiver_id=? WHERE id=?";
    private static final String SQL_GET_LAST_GENERATED_ID = "SELECT LAST_INSERT_ID()";

    public GameDAO(){
        connection = new WrapperConnection();
    }

    @Override
    public List<Game> findAll() throws DAOException {
        return null;
    }

    @Override
    public Game findEntityById(int id) {
        return null;
    }

    @Override
    public Game findEntityByName(String name) throws DAOException {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Game entity) {
        return false;
    }

    @Override
    public void create(Game entity) throws DAOException {
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_INSERT_GAME);
            st.setInt(1, entity.getId());
            st.setString(2, entity.getLobby());
            st.setString(3, "2017-02-02");
            st.executeUpdate();
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    @Override
    public Game update(Game entity) throws DAOException {
        return null;
    }

    @Override
    public void close() {
        connection.close();
    }
}
