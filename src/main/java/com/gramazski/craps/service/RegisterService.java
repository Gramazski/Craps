package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;
import com.gramazski.craps.util.CipherHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gs on 11.03.2017.
 */
public class RegisterService {
    private final static Logger logger = LogManager.getLogger(RegisterService.class);

    /**
     * @param user
     * @return
     */
    public boolean tryAddUser(User user){
        try(UserDAO userDAO = new UserDAO()) {
            if (!(userDAO.isUserNameExists(user.getUserName()) && userDAO.isEmailExists(user.getEmail()))){
                UserService userService = new UserService();
                user.setPassword(CipherHandler.encryptString(user.getPassword()));
                userService.createUser(user);
                user.setPassword("");

                return true;
            }
            else {
                user.setUserName("");
                user.setEmail("");
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        };

        return false;
    }
}
