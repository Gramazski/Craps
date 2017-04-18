package com.gramazski.craps.listener;

import com.gramazski.craps.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by gs on 01.03.2017.
 */
@WebListener
public class ContextServletListenerImpl implements ServletContextListener {
    /**
     * @param servletContextEvent
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
    }

    /**
     * @param servletContextEvent
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().closePool();
    }
}
