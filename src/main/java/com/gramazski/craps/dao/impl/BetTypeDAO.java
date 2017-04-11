package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.BetType;
import com.gramazski.craps.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BetTypeDAO extends AbstractDAO<BetType> {
    private WrapperConnection connection;
    private static final String SQL_GET_LOSE_COMBINATIONS_FOR_BET =
            "SELECT bet_type_has_combination.bet_type_id, bet_type_has_combination.combination_result, combination.number " +
            "FROM bet_type_has_combination JOIN combination ON bet_type_has_combination.combination_id=combination.id " +
            "WHERE bet_type_has_combination.bet_type_id=? " +
            "AND bet_type_has_combination.combination_result=?";
    private static final String SQL_SELECT_ALL_BET_TYPES = "SELECT *" +
            "FROM bet_type";
    private static final String SQL_SELECT_ID_BY_TYPE = "SELECT id FROM bet_type WHERE type=?";

    public BetTypeDAO(){
        connection = new WrapperConnection();
    }

    @Override
    public List<BetType> findAll() throws DAOException {
        List<BetType> betTypeList = new ArrayList<>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_BET_TYPES);
            while (resultSet.next()) {
                BetType betType = new BetType();
                betType.setId(resultSet.getInt("id"));
                betType.setDescription(resultSet.getString("description"));
                betType.setKoef(resultSet.getDouble("koef"));
                betType.setType(resultSet.getString("type"));
                betType.setLoseNumbers(getCombinations(betType.getId(), false));
                betType.setWinNumbers(getCombinations(betType.getId(), true));

                betTypeList.add(betType);
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return betTypeList;
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

    public int getIdByType(String type) throws DAOException{
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_SELECT_ID_BY_TYPE);
            st.setString(1, type);
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

    private int[] getCombinations(int betId, boolean isWin) throws DAOException{
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_GET_LOSE_COMBINATIONS_FOR_BET);
            st.setInt(1, betId);
            st.setBoolean(2, isWin);
            ResultSet resultSet = st.executeQuery();
            ArrayList<Integer> combinationsList = new ArrayList<>();
            while (resultSet.next()){
                combinationsList.add(resultSet.getInt("number"));
            }

            return getArrayFromList(combinationsList);
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    private int[] getArrayFromList(ArrayList<Integer> combinationList){
        int[] resultArray = new int[combinationList.size()];

        for (int i = 0; i < combinationList.size(); i++){
            resultArray[i] = combinationList.get(i);
        }

        return resultArray;
    }
}
