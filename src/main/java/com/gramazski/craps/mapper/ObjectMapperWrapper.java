package com.gramazski.craps.mapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;

/**
 * Created by gs on 11.03.2017.
 */
public class ObjectMapperWrapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readValue(DataInput src, Class<T> valueType) throws IOException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(DataInput src, JavaType valueType) throws IOException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(File src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(File src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(File src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(URL src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(URL src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(URL src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(content, valueType);
    }

    public static <T> T readValue(String content, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(content, valueTypeRef);
    }

    public static <T> T readValue(String content, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(content, valueType);
    }

    public static <T> T readValue(Reader src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(Reader src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(Reader src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(InputStream src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(InputStream src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(InputStream src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(byte[] src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(byte[] src, int offset, int len, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, offset, len, valueType);
    }

    public static <T> T readValue(byte[] src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(byte[] src, int offset, int len, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, offset, len, valueTypeRef);
    }

    public static <T> T readValue(byte[] src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(byte[] src, int offset, int len, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, offset, len, valueType);
    }

    public static void writeValue(File resultFile, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        objectMapper.writeValue(resultFile, value);
    }

    public static void writeValue(OutputStream out, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        objectMapper.writeValue(out, value);
    }

    public static void writeValue(DataOutput out, Object value) throws IOException {
        objectMapper.writeValue(out, value);
    }

    public static void writeValue(Writer w, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        objectMapper.writeValue(w, value);
    }

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}
