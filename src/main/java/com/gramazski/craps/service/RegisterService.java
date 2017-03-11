package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;

/**
 * Created by gs on 11.03.2017.
 */
public class RegisterService {
    private User user = null;

    public boolean tryAddUser(User user){
        try(UserDAO userDAO = new UserDAO()) {
            if (userDAO.findEntityByName(user.getUserName()) == null){
                user.setCreateTime("2012-02-12");
                user.setAvatar("assets/img/user/cube.jpg");
                user.setAdmin(false);
                userDAO.create(user);
                this.user = userDAO.findEntityByName(user.getUserName());
                return true;
            }
            else {
                user.setUserName("");
            }
        } catch (DAOException e) {
            e.printStackTrace();
        };

        return false;
    }

    public User getUser() {
        return user;
    }
}
