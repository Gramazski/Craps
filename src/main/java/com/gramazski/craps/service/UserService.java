package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.MessageDAO;
import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.Message;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;

import java.util.ArrayList;

public class UserService {
    public User createUser(User user){
        try(UserDAO userDAO = new UserDAO()) {
            user.setCreateTime("2012-02-12");
            user.setAvatar("assets/img/user/cube.jpg");
            user.setMessages(new ArrayList<Message>());
            user.setAdmin(false);
            user.setBanned(false);
            userDAO.create(user);
        } catch (DAOException e){
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByUserName(String userName){
        User user = null;
        try(UserDAO userDAO = new UserDAO(); MessageDAO messageDAO = new MessageDAO()) {
            user = userDAO.findEntityByName(userName);
            user.setMessages(messageDAO.getAllMessagesForUser(user.getId()));
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
