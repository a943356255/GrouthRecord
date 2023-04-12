package com.example.springboot_vue.controller.verify_controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.pojo.enum_pojo.ResultCode;
import com.example.springboot_vue.pojo.verify.ResultVO;
import com.example.springboot_vue.pojo.verify.VerifyUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
public class VerifyController {

    @RequestMapping("/user")
    public ResultVO<JSONObject> addUser(@RequestBody @Validated VerifyUser verifyUser) {

        int[] arr = new int[3];

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", verifyUser);

        return new ResultVO<>(ResultCode.SUCCESS, jsonObject);
    }

    @RequestMapping("/test")
    public ResultVO<VerifyUser> test(@RequestBody @Validated VerifyUser verifyUser) {

        return ResultVO.success(verifyUser);
    }
}
