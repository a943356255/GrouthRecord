package com.example.springboot_vue.my_exception;

import lombok.Getter;

@Getter
public class TokenVerifyException extends RuntimeException {

    private int code;
    private String msg;

    public TokenVerifyException() {
        this(1002, "token过期，请重新登录");
    }

    public TokenVerifyException(String msg) {
        this(1002, msg);
    }

    public TokenVerifyException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
