package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;

/**
 * Created by gs on 12.03.2017.
 */
public class UpdateUserService {
    public boolean tryUpdateUser(User user){
        try(UserDAO userDAO = new UserDAO()) {
            userDAO.update(user);
            return true;
        }
        catch (DAOException e){
            //logging
        }

        return false;
    }
}
