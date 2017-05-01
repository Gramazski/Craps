package com.gramazski.craps.dao.impl;

import com.gramazski.craps.connector.WrapperConnection;
import com.gramazski.craps.dao.AbstractDAO;
import com.gramazski.craps.entity.impl.SoundDescription;
import com.gramazski.craps.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SoundsDAO extends AbstractDAO<SoundDescription> {
    private WrapperConnection connection;
    private static final String SQL_SELECT_ALL_SOUNDS = "SELECT id, name, path FROM sounds";
    private static final String SQL_INSERT_SOUND = "INSERT INTO sounds(id, name, path) VALUES(?,?,?)";
    private static final String SQL_GET_LAST_GENERATED_ID = "SELECT LAST_INSERT_ID()";

    public SoundsDAO(){
        connection = new WrapperConnection();
    }

    @Override
    public List<SoundDescription> findAll() throws DAOException {
        List<SoundDescription> sounds = new ArrayList<>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_SOUNDS);
            while (resultSet.next()) {
                SoundDescription soundDescription = new SoundDescription();
                soundDescription.setId(resultSet.getInt("id"));
                soundDescription.setName(resultSet.getString("name"));
                soundDescription.setPath(resultSet.getString("path"));

                sounds.add(soundDescription);
            }
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }

        return sounds;
    }

    @Override
    public SoundDescription findEntityByName(String name) throws DAOException {
        return null;
    }

    @Override
    public void create(SoundDescription entity) throws DAOException {
        PreparedStatement st = null;
        try {
            st = connection.createPreparedStatement(SQL_INSERT_SOUND);
            st.setString(1, null);
            st.setString(2, entity.getName());
            st.setString(3, entity.getPath());
            st.executeUpdate();
            entity.setId(getLastId());
        }catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e.getMessage(), e);
        } finally {
            connection.closeStatement(st);
        }
    }

    @Override
    public SoundDescription update(SoundDescription entity) throws DAOException {
        return null;
    }

    @Override
    public void close() {
        connection.close();
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
