package com.gramazski.craps.service;

import com.gramazski.craps.exception.HandlerException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gs on 21.02.2017.
 */
public class FileUploaderService {
    private static final String RESOURCE_DIRECTORY = "assets/img/user/";

    /**
     * @param request
     * @param rootPath
     * Parsing multipart data request
     * @return parameter map from request
     * @throws HandlerException
     */
    public Map<String, String> uploadFileFromRequest(HttpServletRequest request, String rootPath) throws HandlerException{
        return processMultipartRequest(request, rootPath);
    }

    private Map<String, String> processMultipartRequest(HttpServletRequest request, String rootPath) throws HandlerException {
        Map<String, String> paramMap = new HashMap<>();
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        Iterator<FileItem> iterator = null;

        try {
            iterator = servletFileUpload.parseRequest(request).iterator();
        } catch (FileUploadException e) {
            throw new HandlerException("Servlet file upload can't parse request. Course: " + e.getMessage(), e);
        }

        FileItem fileToUpload = null;
        String fileName = null;
        while (iterator.hasNext()) {
            FileItem item = iterator.next();
            if (!item.isFormField()) {
                if ("file".equals(item.getFieldName())) {
                    fileToUpload = item;
                    fileName = item.getName();
                }
            }
            else {
                paramMap.put(item.getFieldName(), item.getString());
            }
        }

        if (fileToUpload != null && fileName != null) {
            paramMap.put("file", saveFile(fileToUpload, fileName, rootPath));
        }

        return paramMap;
    }


    /**
     * @param fileItem
     * @param fileName
     * @param rootPath
     * @return
     * @throws HandlerException
     */
    private String saveFile(FileItem fileItem, String fileName, String rootPath) throws HandlerException {
        String filePath = RESOURCE_DIRECTORY + fileName;
        File uploadedFile = new File(rootPath + RESOURCE_DIRECTORY, fileName);
        try {
            fileItem.write(uploadedFile);
        } catch (Exception e) {
            throw new HandlerException("Can't write data to file: " + rootPath + RESOURCE_DIRECTORY + fileName, e);
        }

        return filePath;
    }
}
