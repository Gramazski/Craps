package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;

/**
 * Created by gs on 27.02.2017.
 */
public class LoginService {
    private User user  = null;

    public boolean checkUser(String login, String password){
        User user = getUserByLogin(login);

        if ((user != null) && (user.getPassword().equals(password))){
            this.user = user;
            return true;
        }
        else {
            this.user = null;
            return false;
        }
    }

    public User getUser(){
        return user;
    }

    private User getUserByLogin(String login){
        User user = null;
        try(UserDAO userDAO = new UserDAO()) {
            user = userDAO.findEntityByName(login);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
