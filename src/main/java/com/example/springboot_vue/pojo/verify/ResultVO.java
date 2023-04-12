package com.example.springboot_vue.pojo.verify;

import com.example.springboot_vue.pojo.enum_pojo.ResultCode;
import lombok.Data;

@Data
public class ResultVO<T> {
    /**
     * 状态码，比如1000代表响应成功
     */
    private int code;
    /**
     * 响应信息，用来说明响应情况
     */
    private String msg;
    /**
     * 响应的具体数据
     */
    private T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public ResultVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultVO success(Object data) {
        return new ResultVO(ResultCode.SUCCESS, data);
    }

    public static ResultVO fail() {
        return new ResultVO(ResultCode.FAILED, null);
    }

    public static ResultVO fail(String msg) {
        return new ResultVO(ResultCode.FAILED.getCode(), msg, null);
    }

    public static ResultVO fail(ResultCode resultCode) {
        return new ResultVO(resultCode, null);
    }
}
