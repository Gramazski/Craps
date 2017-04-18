package com.gramazski.craps.manager;

import com.gramazski.craps.exception.ResourceManagerException;

import java.util.*;

/**
 * Created by gs on 22.01.2017.
 */
public class ResourceManager {
    private static ResourceManager instance = new ResourceManager();

    private ResourceManager() { }

    /**
     * @return
     */
    public static ResourceManager getInstance() {
        return instance;
    }

    /**
     * @param key
     * @param bundleName
     * @param locale
     * @return
     * @throws ResourceManagerException
     */
    public String getString(String key, String bundleName, Locale locale) throws ResourceManagerException {
        ResourceBundle resourceBundle = createBundle(bundleName, locale);
        if ((resourceBundle != null) && resourceBundle.containsKey(key)){
            return resourceBundle.getString(key);
        }

        throw new ResourceManagerException("Key not exists.");
    }

    /**
     * @param bundleName
     * @param locale
     * @return
     * @throws ResourceManagerException
     */
    public Properties getAllProperties(String bundleName, Locale locale) throws ResourceManagerException {
        ResourceBundle resourceBundle = createBundle(bundleName, locale);
        Properties properties = new Properties();
        Enumeration<String> keys = resourceBundle.getKeys();

        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            properties.put(key, resourceBundle.getString(key));
        }

        return properties;
    }

    /**
     * @param bundleName
     * @param locale
     * @return
     * @throws ResourceManagerException
     */
    private ResourceBundle createBundle(String bundleName, Locale locale) throws ResourceManagerException {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, locale);
            return resourceBundle;
        }
        catch (MissingResourceException ex){
            throw new ResourceManagerException("Can't create resource bundle, resource not found. Course: " + ex.getMessage(), ex);
        }
    }
}
