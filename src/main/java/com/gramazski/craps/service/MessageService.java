package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.MessageDAO;
import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.Message;
import com.gramazski.craps.exception.DAOException;
import com.gramazski.craps.util.DateHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageService {
    private final static Logger logger = LogManager.getLogger(MessageService.class);

    public boolean trySaveMessage(Message message){
        try(MessageDAO messageDAO = new MessageDAO(); UserDAO userDAO = new UserDAO()) {
            if (userDAO.isUserNameExists(message.getReceiver())){
                message.setCreateDate(DateHandler.getCurrentDate());
                message.setStatus(Message.Status.GET);
                messageDAO.create(message);

                return true;
            }
        } catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return false;
    }
}
