package com.gramazski.craps.service;

import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.util.CipherHandler;

/**
 * Created by gs on 27.02.2017.
 */
public class LoginService {
    private User user  = null;

    /**
     * @param login
     * @param password
     * @return
     */
    public boolean checkUser(String login, String password){
        UserService userService = new UserService();
        User user = userService.getUserByUserName(login);

        if ((user != null) && (user.getPassword().equals(CipherHandler.encryptString(password)))){
            this.user = user;
            this.user.setPassword("");
            return true;
        }
        else {
            this.user = null;
            return false;
        }
    }

    /**
     * @return
     */
    public User getUser(){
        return user;
    }
}
