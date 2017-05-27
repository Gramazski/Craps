package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BanService {
    private final static Logger logger = LogManager.getLogger(BanService.class);

    /**
     * @param user
     * Make user ban
     * @param isBanning
     */
    public void makeBanUser(User user, boolean isBanning){
        try(UserDAO userDAO = new UserDAO()) {
            if (userDAO.isUserNameExists(user.getUserName())){
                user.setBanned(isBanning);

                userDAO.update(user);
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        };
    }
}
