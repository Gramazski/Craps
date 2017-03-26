package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gs on 12.03.2017.
 */
public class UpdateUserService {
    private final static Logger logger = LogManager.getLogger(UpdateUserService.class);

    public boolean tryUpdateUser(User user){
        try(UserDAO userDAO = new UserDAO()) {
            userDAO.update(user);
            return true;
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return false;
    }
}
