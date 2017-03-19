package com.gramazski.craps.entity.impl;

import com.gramazski.craps.entity.Entity;

/**
 * Created by gs on 12.03.2017.
 */
public class Message extends Entity{
    private String title;
    private String body;
    private String createDate;
    private Status status;
    private String sender;
    private String receiver;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public enum Status{
        GET, READ, DELETED
    }
}
