package com.gramazski.craps.pool;

import com.gramazski.craps.exception.ResourceManagerException;
import com.gramazski.craps.manager.ResourceManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gs on 19.02.2017.
 */
public class ConnectionPool {
    private static final String CONFIG_FILE_NAME = "connectionConfig";
    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();
    private BlockingQueue<Connection> connections;
    private final int CON_COUNT;
    private static AtomicInteger instanceFlag = new AtomicInteger(0);

    private ConnectionPool() {
        try {
            Properties properties = ResourceManager.getInstance().getAllProperties(CONFIG_FILE_NAME, Locale.getDefault());
            Class.forName(properties.getProperty("driver_name"));
            CON_COUNT = Integer.valueOf(properties.getProperty("con_count"));
            connections = new ArrayBlockingQueue<Connection>(CON_COUNT);
            for (int i = 0; i < CON_COUNT; i++){
                connections.put(createConnection(properties.getProperty("databaseUrl"), properties));
            }
        }
        catch (InterruptedException ex){
            //logging
            throw new RuntimeException("Can't put connection in Blocking queue. Course: " + ex.getMessage(), ex);
        }
        catch (ClassNotFoundException ex){
            //logging
            throw new RuntimeException("Can't register jdbc Driver class. Course: " + ex.getMessage(), ex);
        }
        catch (NumberFormatException ex){
            //logging
            throw new RuntimeException("Can't get connections count. Course: " + ex.getMessage(), ex);
        }
        catch (ResourceManagerException ex){
            //logging
            throw new RuntimeException("Can't find config file. Course: " + ex.getMessage(), ex);
        }
    }

    public static ConnectionPool getInstance(){
        if (instanceFlag.intValue() == 0){
            lock.lock();
            try {
                if (instance == null){
                    instance = new ConnectionPool();
                    instanceFlag.incrementAndGet();
                }
            }
            finally {
                lock.unlock();
            }

        }

        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            //logging ??
        }
        return connection;
    }

    public void returnConnection(Connection connection){
        try {
            if (connection != null){
                connections.put(connection);
            }
        } catch (InterruptedException e) {
            //logging ??
        }
    }

    public void closePool(){
        if (instance != null){
            for (int i = 0; i < CON_COUNT; i++){
                try {
                    Connection connection = connections.take();
                    connection.close();
                } catch (InterruptedException | SQLException e) {
                    //logging
                }
            }

            try {
                Enumeration<Driver> drivers = DriverManager.getDrivers();
                while (drivers.hasMoreElements()) {
                    Driver driver = drivers.nextElement();
                    DriverManager.deregisterDriver(driver);
                }
            } catch (SQLException e) {
                //LOGGER.log(Level.ERROR, e + " DriverManager wasn't found.");
            }
        }
    }

    private Connection createConnection(String databaseUrl, Properties properties){
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(databaseUrl, properties);
        } catch (SQLException e) {
            //logging
            throw new RuntimeException("Creating connection failed. Course: " + e.getMessage(), e);
        }

        return connection;
    }
}
