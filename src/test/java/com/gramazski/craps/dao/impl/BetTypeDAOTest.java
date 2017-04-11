package com.gramazski.craps.dao.impl;

import com.gramazski.craps.entity.impl.BetType;
import com.gramazski.craps.exception.DAOException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BetTypeDAOTest {
    @Test
    public void findAll() throws Exception {
        List<BetType> betTypeList;
        try(BetTypeDAO betTypeDAO = new BetTypeDAO()) {
            betTypeList = betTypeDAO.findAll();
        }
        catch (DAOException e){
            Assert.fail(e.getMessage());
        }
    }

}