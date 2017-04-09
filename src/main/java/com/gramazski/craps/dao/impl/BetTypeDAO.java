package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.BetType;
import com.gramazski.craps.exception.DAOException;

import java.util.List;

public class BetTypeDAO extends AbstractDAO<BetType> {
    private WrapperConnection connection;
    private static final String SQL_SELECT_ALL_BET_TYPES = "SELECT id, title, create_date, body, status, sender_id," +
            "receiver_id FROM message";
    private static final String SQL_SELECT_ALL_MESSAGES_FOR_USER = "SELECT id, title, create_date, body, status, sender_id," +
            "receiver_id FROM message WHERE sender_id=? OR receiver_id=?";
    private static final String SQL_INSERT_GAME = "INSERT INTO game(id, lobbyname, time VALUES(?,?,?)";
    private static final String SQL_SELECT_MESSAGE_BY_ID = "SELECT id, title, create_date, body, sender_id," +
            "status, receiver_id FROM message WHERE id = ?";
    private static final String SQL_UPDATE_MESSAGE = "UPDATE user SET id=?, title=?, create_date=?, body=?," +
            "status=?, sender_id=?, receiver_id=? WHERE id=?";
    private static final String SQL_GET_LAST_GENERATED_ID = "SELECT LAST_INSERT_ID()";

    public BetTypeDAO(){
        connection = new WrapperConnection();
    }

    @Override
    public List<BetType> findAll() throws DAOException {
        return null;
    }

    @Override
    public BetType findEntityById(int id) {
        return null;
    }

    @Override
    public BetType findEntityByName(String name) throws DAOException {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(BetType entity) {
        return false;
    }

    @Override
    public void create(BetType entity) throws DAOException {

    }

    @Override
    public BetType update(BetType entity) throws DAOException {
        return null;
    }

    @Override
    public void close() {
        connection.close();
    }
}
