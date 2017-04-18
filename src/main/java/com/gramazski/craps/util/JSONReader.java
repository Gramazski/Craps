package com.gramazski.craps.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by gs on 12.03.2017.
 */
public class JSONReader {
    private final static Logger logger = LogManager.getLogger(JSONReader.class);

    /**
     * @param request
     * @return
     */
    public static String readJsonString(HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = request.getReader();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }


        return sb.toString();
    }
}
