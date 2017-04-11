package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.Bet;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GameDAO extends AbstractDAO<Game> {
    private WrapperConnection connection;
    private static final String SQL_INSERT_GAME = "INSERT INTO game(id, lobbyname, min, max, type) VALUES(?,?,?,?,?)";
    private static final String SQL_INSERT_BET = "INSERT INTO game_has_user(id, game_id, user_id, is_win, amount," +
            " bet_type_id, time) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_GET_LAST_ID = "SELECT MAX(id) FROM game";

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
            st.setInt(3, entity.getMinBet());
            st.setInt(4, entity.getMaxBet());
            st.setString(5, entity.getType());
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

    public int getLastId() throws DAOException{
        Statement st = null;
        int id = 0;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_GET_LAST_ID);
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

    public void saveBet(Bet bet, int userId, boolean isWin) throws DAOException{
        PreparedStatement st = null;
        try(BetTypeDAO betTypeDAO = new BetTypeDAO()) {
            st = connection.createPreparedStatement(SQL_INSERT_BET);
            st.setString(1, null);
            st.setInt(2, bet.getGameId());
            st.setInt(3, userId);
            st.setBoolean(4, isWin);
            st.setInt(5, bet.getAmount());
            st.setInt(6, betTypeDAO.getIdByType(bet.getType()));
            st.setString(7, bet.getTime());
            st.executeUpdate();
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    @Override
    public void close() {
        connection.close();
    }
}
