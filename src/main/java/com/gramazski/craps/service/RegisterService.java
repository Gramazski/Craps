package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;

/**
 * Created by gs on 11.03.2017.
 */
public class RegisterService {
    public boolean tryAddUser(User user){
        try(UserDAO userDAO = new UserDAO()) {
            if (!(userDAO.isUserNameExists(user.getUserName()) && userDAO.isEmailExists(user.getEmail()))){
                UserService userService = new UserService();
                userService.createUser(user);

                return true;
            }
            else {
                user.setUserName("");
                user.setEmail("");
            }
        } catch (DAOException e) {
            e.printStackTrace();
        };

        return false;
    }
}
