package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.UserDAO;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.exception.DAOException;
import com.gramazski.craps.exception.HandlerException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by gs on 12.03.2017.
 */
public class UpdateUserService {
    private final static Logger logger = LogManager.getLogger(UpdateUserService.class);

    public User tryUpdateUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        FileUploaderService uploaderService = new FileUploaderService();
        try(UserDAO userDAO = new UserDAO()) {
            Map<String, String> paramMap = uploaderService.uploadFileFromRequest(request, session.getServletContext().getRealPath("/"));
            user.setAvatar(paramMap.get("avatar"));
            user.setName(paramMap.get("name"));
            user.setSurname(paramMap.get("surname"));
            user.setBirthday(paramMap.get("birthday"));
            userDAO.update(user);

            return user;
        }
        catch (DAOException | HandlerException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return (User) session.getAttribute("user");
    }
}
