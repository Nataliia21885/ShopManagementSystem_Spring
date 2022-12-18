package com.application.spring.model.entity;

import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
public class ExceptionResponse<T> {
    public enum Type {
        SUCCESS, ERROR
    }

    @Builder.Default
    private Type type = Type.SUCCESS;
    private String description;
    @Builder.Default
    private int status = 200;
    @Builder.Default
    private String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
}
