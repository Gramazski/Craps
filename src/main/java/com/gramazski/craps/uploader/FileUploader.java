package com.gramazski.craps.uploader;

import com.gramazski.craps.exception.HandlerException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;

/**
 * Created by gs on 21.02.2017.
 */
public class FileUploader {

    public String uploadFileFromRequest(HttpServletRequest request, String rootPath) throws HandlerException{
        return processMultipartRequest(request, rootPath);
    }

    private String processMultipartRequest(HttpServletRequest request, String rootPath) throws HandlerException {
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
                if ("file_to_upload".equals(item.getFieldName())) {
                    fileToUpload = item;
                    fileName = item.getName();
                }
            }
        }

        if (fileToUpload != null && fileName != null) {
            return saveFile(fileToUpload, fileName, rootPath);
        }

        return "";
    }


    private String saveFile(FileItem fileItem, String fileName, String rootPath) throws HandlerException {
        String filePath = rootPath + "xml/" + fileName;
        File uploadedFile = new File(rootPath + "xml/", fileName);
        try {
            fileItem.write(uploadedFile);
        } catch (Exception e) {
            throw new HandlerException("Can't write data to file: " + rootPath + "xml/" + fileName, e);
        }

        return filePath;
    }
}
