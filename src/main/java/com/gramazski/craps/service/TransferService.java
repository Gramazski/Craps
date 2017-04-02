package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.TransferDAO;
import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.Transfer;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;
import com.gramazski.craps.util.DateHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransferService {
    private final static Logger logger = LogManager.getLogger(RegisterService.class);

    public boolean makeTransfer(Transfer transfer, User user){
        if (checkTransfer(transfer, user)){
            return false;
        }

        try(TransferDAO transferDAO = new TransferDAO(); UserDAO userDAO = new UserDAO()) {
            transfer.setUserId(user.getId());
            transfer.setDate(DateHandler.getCurrentDate());
            transferDAO.create(transfer);
            if (transfer.isIncoming()){
                user.setAmount(user.getAmount() + transfer.getAmount());
            }
            else {
                user.setAmount(user.getAmount() - transfer.getAmount());
            }

            user.addTransfer(transfer);
            userDAO.update(user);
            return true;
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return false;
    }

    private boolean checkTransfer(Transfer transfer, User user){
        return !transfer.isIncoming() && (transfer.getAmount() > user.getAmount());
    }
}
