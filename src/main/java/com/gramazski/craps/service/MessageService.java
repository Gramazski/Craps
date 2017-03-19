package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.MessageDAO;
import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.Message;
import com.gramazski.craps.exception.DAOException;

public class MessageService {
    public void saveMessage(Message message){
        try(MessageDAO messageDAO = new MessageDAO(); UserDAO userDAO = new UserDAO()) {
            if (userDAO.isUserNameExists(message.getReceiver())){
                message.setStatus(Message.Status.GET);
                messageDAO.create(message);
            }
        } catch (DAOException e){
            e.printStackTrace();
        }
    }
}
