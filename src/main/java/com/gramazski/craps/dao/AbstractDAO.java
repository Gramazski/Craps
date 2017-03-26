package com.gramazski.craps.dao;

import com.gramazski.craps.entity.Entity;
import com.gramazski.craps.exception.DAOException;

import java.util.List;

/**
 * Created by gs on 19.02.2017.
 */
public abstract class AbstractDAO<T extends Entity> implements AutoCloseable {
    public abstract List<T> findAll() throws DAOException;
    public abstract T findEntityById(int id);
    public abstract T findEntityByName(String name) throws DAOException;
    public abstract boolean delete(int id);
    public abstract boolean delete(T entity);
    public abstract void create(T entity) throws DAOException;
    public abstract T update(T entity) throws DAOException;
    public abstract void close();
}
