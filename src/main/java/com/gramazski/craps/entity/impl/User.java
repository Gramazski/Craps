package com.gramazski.craps.entity.impl;

import com.gramazski.craps.entity.Entity;

/**
 * Created by gs on 19.02.2017.
 */
public class User extends Entity {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user: { username: " + userName + ", password: " + password +"}";
    }
}
