package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.MessageDAO;
import com.gramazski.craps.dao.impl.TransferDAO;
import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.Message;
import com.gramazski.craps.entity.impl.Transfer;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;
import com.gramazski.craps.util.DateHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class UserService {
    private final static Logger logger = LogManager.getLogger(UserService.class);

    public User createUser(User user){
        try(UserDAO userDAO = new UserDAO()) {
            user.setCreateTime(DateHandler.getCurrentDate());
            user.setAvatar("assets/img/user/cube.jpg");
            user.setMessages(new ArrayList<Message>());
            user.setTransfers(new ArrayList<Transfer>());
            user.setAdmin(false);
            user.setBanned(false);
            userDAO.create(user);
        } catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return user;
    }

    public User getUserByUserName(String userName){
        User user = null;
        try(UserDAO userDAO = new UserDAO(); MessageDAO messageDAO = new MessageDAO();
            TransferDAO transferDAO = new TransferDAO()) {
            user = userDAO.findEntityByName(userName);
            user.setMessages(messageDAO.getAllMessagesForUser(user.getId()));
            user.setTransfers(transferDAO.getAllTransfersForUser(user.getId()));
        } catch (DAOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }

        return user;
    }
}
