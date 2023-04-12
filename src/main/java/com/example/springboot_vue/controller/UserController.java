package com.example.springboot_vue.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.UserMapper;
import com.example.springboot_vue.pojo.login.AsyncRouterMap;
import com.example.springboot_vue.pojo.verify.ResultVO;
import com.example.springboot_vue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userServiceImpl;

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserMapper userMapper;

    // 存放验证码
    public static Map<String, String> map = new HashMap<>();

    @RequestMapping("/getCode")
    public JSONObject getCode(@RequestParam("username") String phone) {

        String code = userServiceImpl.getCode(phone);
        map.put(phone, code);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneCode", code);

        return jsonObject;
    }

    @RequestMapping("/register")
    public JSONObject register(@RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();

        String phone = map.get("phone");
        String code = UserController.map.get(phone);

        // 验证码不一致
        if (code == null || !code.equals(map.get("code"))) {
            jsonObject.put("code", -1);
            return jsonObject;
        }

        String token = userServiceImpl.register(phone, code);
        if (token == null) {
            jsonObject.put("code", -1);
            return jsonObject;
        }
        jsonObject.put("token", token);
        jsonObject.put("code", 200);

        return jsonObject;
    }

    @RequestMapping("/login")
    public JSONObject login(@RequestBody Map<String, String> map) {
        return userServiceImpl.login(map);
    }

    @RequestMapping("/getInfo")
    public JSONObject getInfo() {
        // 从请求头中获取token
        String token = request.getHeader("token");

        return userServiceImpl.getInfo(token);
    }

    @RequestMapping("/getRoles")
    public ResultVO<List<String>> getRoles() {
        return userServiceImpl.getRoles();
    }

    @RequestMapping("/insertController")
    public ResultVO<String> insertController(@RequestBody Map<String, Object> map) {
        return userServiceImpl.insertController(map);
    }

    @RequestMapping("/getRouter")
    public ResultVO<List<AsyncRouterMap>> getRouter() {
        ArrayList<AsyncRouterMap> list = userMapper.getRouter();
        return new ResultVO<>(list);
    }

    @RequestMapping("/insertRouter")
    public ResultVO<String> insertRouter(@RequestBody @Validated AsyncRouterMap asyncRouterMap) {

        System.out.println(asyncRouterMap.toString());
        System.out.println(asyncRouterMap.getComponent());

        System.out.println("打印完，是否为空");

        userMapper.insertRouter(asyncRouterMap);
        return new ResultVO<>("success");
    }
}