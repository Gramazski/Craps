package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.Transfer;
import com.gramazski.craps.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransferDAO extends AbstractDAO<Transfer> {
    private WrapperConnection connection;
    private static final String SQL_SELECT_ALL_TRANSFERS = "SELECT id, amount, time, accountnumber," +
            "pay_way_flag, user_id FROM payment";
    private static final String SQL_SELECT_ALL_TRANSFERS_FOR_USER = "SELECT id, amount, time, accountnumber," +
            "pay_way_flag FROM payment WHERE user_id=?";
    private static final String SQL_INSERT_TRANSFER = "INSERT INTO payment(id, amount, time, accountnumber," +
            "pay_way_flag, user_id) VALUES(?,?,?,?,?,?)";
    private static final String SQL_SELECT_TRANSFER_BY_ID = "SELECT id, amount, time, accountnumber," +
            "pay_way_flag, user_id FROM payment WHERE id = ?";
    private static final String SQL_GET_LAST_GENERATED_ID = "SELECT LAST_INSERT_ID()";

    public TransferDAO(){
        connection = new WrapperConnection();
    }

    /**
     * @return
     * @throws DAOException
     */
    @Override
    public List<Transfer> findAll() throws DAOException {
        return null;
    }

    /**
     * @param name
     * @return
     * @throws DAOException
     */
    @Override
    public Transfer findEntityByName(String name) throws DAOException {
        return null;
    }

    /**
     * @param entity
     * @throws DAOException
     */
    @Override
    public void create(Transfer entity) throws DAOException {
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_INSERT_TRANSFER);
            st.setString(1, null);
            st.setInt(2, entity.getAmount());
            st.setString(3, entity.getDate());
            st.setString(4, entity.getAccountNumber());
            st.setBoolean(5, entity.isIncoming());
            st.setInt(6, entity.getUserId());
            st.executeUpdate();
            entity.setId(getLastId());
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
    public Transfer update(Transfer entity) throws DAOException {
        return null;
    }

    @Override
    public void close() {
        connection.close();
    }

    /**
     * @param id
     * @return
     * @throws DAOException
     */
    public List<Transfer> getAllTransfersForUser(int id) throws DAOException{
        List<Transfer> transfers = new ArrayList<>();
        PreparedStatement st = null;
        try(UserDAO userDAO = new UserDAO()) {
            st = connection.createPreparedStatement(SQL_SELECT_ALL_TRANSFERS_FOR_USER);

            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Transfer transfer = new Transfer();
                transfer.setId(resultSet.getInt("id"));
                transfer.setAmount(resultSet.getInt("amount"));
                transfer.setDate(resultSet.getString("time"));
                transfer.setAccountNumber(resultSet.getString("accountnumber"));
                transfer.setIncoming(resultSet.getBoolean("pay_way_flag"));
                transfer.setUserId(id);

                transfers.add(transfer);
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return transfers;
    }

    /**
     * @return
     * @throws DAOException
     */
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
