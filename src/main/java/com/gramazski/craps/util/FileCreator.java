package com.gramazski.craps.util;

import com.gramazski.craps.exception.HandlerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileCreator {
    public static String saveToFile(byte[] bytes, String fileName, String path) throws HandlerException{
        String filePath = fileName;
        File file = new File(path + "/" + fileName);
        try {
            Files.write(file.toPath(), bytes);
        } catch (IOException e) {
            throw new HandlerException(e.getMessage(), e);
        }

        return filePath;
    }
}
