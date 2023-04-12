package com.example.springboot_vue.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonTypeHandler<T> extends BaseTypeHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> clazz;

    public JsonTypeHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    private String toJSON(Object Object) {
        try {
            return objectMapper.writeValueAsString(Object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private T toObject(String context, Class<?> clazz) {
        if (context != null || !context.isEmpty()) {
            try {
                return (T) objectMapper.readValue(context, clazz);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, this.toJSON(o));
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return this.toObject(resultSet.getString(s), clazz);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return this.toObject(resultSet.getString(i), clazz);
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return this.toObject(callableStatement.getString(i), clazz);
    }

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
