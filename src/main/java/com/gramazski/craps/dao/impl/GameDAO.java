package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.Bet;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.entity.impl.PlayedBet;
import com.gramazski.craps.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameDAO extends AbstractDAO<Game> {
    private WrapperConnection connection;
    private static final String SQL_GET_PRESET_GAMES = "SELECT * FROM game WHERE id<?";
    private static final String SQL_INSERT_GAME = "INSERT INTO game(id, lobbyname, min, max, type, max_players_count)" +
            " VALUES(?,?,?,?,?,?)";
    private static final String SQL_INSERT_BET = "INSERT INTO game_has_user(id, game_id, user_id, is_win," +
            " amount, bet_type_id, time) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_GET_LAST_ID = "SELECT MAX(id) FROM game";
    private static final String SQL_GET_GAME_HISTORY_FOR_USER =
            "SELECT game_history.amount, game_history.is_win, game_history.time, game_history.lobbyname, " +
            "game_history.type, bet_type.type AS bet_type, bet_type.koef " +
            "FROM (SELECT game_has_user.amount, game_has_user.is_win, game_has_user.time, game.lobbyname, " +
            "game.type, game_has_user.bet_type_id " +
            "FROM game_has_user JOIN game ON game_has_user.game_id=game.id " +
            "WHERE game_has_user.user_id=?) AS game_history JOIN bet_type  ON game_history.bet_type_id=bet_type.id";

    public GameDAO(){
        connection = new WrapperConnection();
    }

    /**
     * @return
     * @throws DAOException
     */
    @Override
    public List<Game> findAll() throws DAOException {
        return null;
    }

    /**
     * @param name
     * @return
     * @throws DAOException
     */
    @Override
    public Game findEntityByName(String name) throws DAOException {
        return null;
    }

    /**
     * @param entity
     * @throws DAOException
     */
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
            st.setInt(6, entity.getMaxPlayersCount());
            st.executeUpdate();
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
    @Override
    public Game update(Game entity) throws DAOException {
        return null;
    }

    /**
     * @return
     * @throws DAOException
     */
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

    /**
     * @param bet
     * @param userId
     * @param isWin
     * @throws DAOException
     */
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

    /**
     * @param userId
     * @return
     * @throws DAOException
     */
    public List<PlayedBet> getUserPlayedBets(int userId) throws DAOException{
        List<PlayedBet> playedBets = new ArrayList<>();
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_GET_GAME_HISTORY_FOR_USER);
            st.setInt(1, userId);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()){
                PlayedBet playedBet = new PlayedBet();
                playedBet.setTime(resultSet.getString("time"));
                playedBet.setLobby(resultSet.getString("lobbyname"));
                playedBet.setType(resultSet.getString("type"));
                playedBet.setBetType(resultSet.getString("bet_type"));
                playedBet.setBet(resultSet.getInt("amount"));
                if (resultSet.getBoolean("is_win")){
                    playedBet.setAmount((int)(playedBet.getBet() * (1 + resultSet.getDouble("koef"))));
                }
                else {
                    playedBet.setAmount(-playedBet.getBet());
                }

                playedBets.add(playedBet);
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return playedBets;
    }

    /**
     * @param count
     * @return
     * @throws DAOException
     */
    public List<Game> getStartGameList(int count) throws DAOException{
        List<Game> games = new ArrayList<>();
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_GET_PRESET_GAMES);
            st.setInt(1, count + 1);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next() && (count > 0)){
                Game game = new Game();
                game.setId(resultSet.getInt("id"));
                game.setType(resultSet.getString("type"));
                game.setLobby(resultSet.getString("lobbyname"));
                game.setMaxBet(resultSet.getInt("max"));
                game.setMinBet(resultSet.getInt("min"));
                game.setMaxPlayersCount(resultSet.getInt("max_players_count"));

                games.add(game);
                count--;
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return games;
    }

    @Override
    public void close() {
        connection.close();
    }
}
