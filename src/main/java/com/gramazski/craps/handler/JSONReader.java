package com.gramazski.craps.handler;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by gs on 12.03.2017.
 */
public class JSONReader {
    //own exception
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
            //e.printStackTrace();
        }


        return sb.toString();
    }
}
