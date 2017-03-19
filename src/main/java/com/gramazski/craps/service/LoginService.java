package com.gramazski.craps.service;

import com.gramazski.craps.entity.impl.User;

/**
 * Created by gs on 27.02.2017.
 */
public class LoginService {
    private User user  = null;

    public boolean checkUser(String login, String password){
        UserService userService = new UserService();
        User user = userService.getUserByUserName(login);

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
}
