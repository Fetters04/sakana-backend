package com.example.admin_template.entity;

import lombok.Data;

/**
 * @author Fetters
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;
    private boolean ok;

    public Result(int code, String message, T data, boolean ok) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.ok = ok;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data, true);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(500, message, null, false);
    }
}
