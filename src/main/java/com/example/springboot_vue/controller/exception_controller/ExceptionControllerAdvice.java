package com.example.springboot_vue.controller.exception_controller;

import com.example.springboot_vue.my_exception.TokenVerifyException;
import com.example.springboot_vue.pojo.verify.ResultVO;
import com.example.springboot_vue.my_exception.APIException;
import io.jsonwebtoken.SignatureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 * 全局异常处理类
 */

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public ResultVO<String> APIExceptionHandler(APIException e) {
        return new ResultVO<>(e.getCode(), "响应失败", e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 注意哦，这里返回类型是自定义响应体
        return new ResultVO<>(1001, "参数校验失败", objectError.getDefaultMessage());
    }

    @ExceptionHandler(TokenVerifyException.class)
    public ResultVO<String> TokenVerifyExceptionHandler(TokenVerifyException e) {
        return new ResultVO<>(e.getMsg());
    }

//    @ExceptionHandler(Exception.class)
//    public ResultVO<String> ArrayIndexOutOfBoundsExceptionHandler(Exception e) {
//        return new ResultVO<>(1001, "操作失败", null);
//    }

    @ExceptionHandler(SignatureException.class)
    public ResultVO<String> SignatureExceptionHandler(SignatureException e) {
        return new ResultVO<>(1001, "无效的token", null);
    }

    @ExceptionHandler(Exception.class)
    public ResultVO MyExceptionHandler(Exception e) {
        return ResultVO.fail(e.toString());
    }
}
