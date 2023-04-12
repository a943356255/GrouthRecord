package com.example.springboot_vue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.UserMapper;
import com.example.springboot_vue.pojo.user.RouteAndRole;
import com.example.springboot_vue.pojo.user.User;
import com.example.springboot_vue.pojo.user.UserAndRole;
import com.example.springboot_vue.pojo.verify.ResultVO;
import com.example.springboot_vue.service.UserService;
import com.example.springboot_vue.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public String getCode(String phone) {
        Random random = new Random();
        return String.valueOf(random.nextInt(9999));
    }

    @Override
    public String register(String phone, String password) {
        User user = new User();
        user.setPassword(password);
        user.setUsername(phone);

        User user1 = userMapper.selUser(phone);
        if (user1 != null) {
            return null;
        }

        userMapper.register(user);
        return new JwtUtils().getToken(user);
    }

    @Override
    public JSONObject login(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();

        String phone = map.get("phone");
        String password = map.get("password");

        User getUser = userMapper.selUser(phone);

        if (getUser == null) {
            jsonObject.put("code", -1);
            return null;
        }

        if (getUser.getPassword().equals(password)) {
            // 生成token
            String token = new JwtUtils().getToken(getUser);
            System.out.println(token);
            jsonObject.put("token", token);
            jsonObject.put("code", 200);
        } else {
            jsonObject.put("code", -1);
        }

        return jsonObject;
    }

    @Override
    public JSONObject getInfo(String token) {

        JSONObject jsonObject = new JSONObject();

        // 从token中获取到id，然后查询用户更加详细的信息
        Claims claims = JwtUtils.checkToken(token);
        int id = (int) claims.get("id");

        UserAndRole userAndRole = userMapper.getRole(id);

        // 查询到的所有可以访问的路由
        ArrayList<RouteAndRole> routeList = userMapper.getInfo(userAndRole.getRoleId());

        Map<Integer, Integer> map = new HashMap<>();
        // 遍历第一遍，用map记录father的id对应的下标
        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).getParentId() == 0) {
                ArrayList<RouteAndRole> children = new ArrayList<>();
                map.put(routeList.get(i).getId(), i);
                routeList.get(i).setChildren(children);
            }
        }

        // 给children添加元素
        for (RouteAndRole routeAndRole : routeList) {
            if (routeAndRole.getParentId() != 0) {
                // 获取到父亲元素的下标
                int index = map.get(routeAndRole.getParentId());
                routeList.get(index).getChildren().add(routeAndRole);
            }
        }

        // 去除掉重复的子元素
        ArrayList<RouteAndRole> list = new ArrayList<>();
        for (RouteAndRole routeAndRole : routeList) {
            if (routeAndRole.getParentId() == 0) {
                list.add(routeAndRole);
            }
        }

        jsonObject.put("roles", userAndRole.getRole());
        jsonObject.put("pathList", list);
        jsonObject.put("code", 200);

        return jsonObject;
    }

    @Override
    public ResultVO<List<String>> getRoles() {
        List<String> result = userMapper.getRoles();
        return new ResultVO<>(result);
    }

    @Override
    public ResultVO<String> insertController(Map<String, Object> map) {
        List<String> list = (List<String>) map.get("checkedRoles");
        String controller = (String) map.get("controller");

        userMapper.insertController(list, controller);

        return null;
    }
}
