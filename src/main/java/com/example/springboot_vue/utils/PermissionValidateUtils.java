package com.example.springboot_vue.utils;

import com.example.springboot_vue.mapper.UserMapper;
import com.example.springboot_vue.pojo.user.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionValidateUtils {

    @Autowired
    UserMapper userMapper;

    public boolean verifyPermission(String token) {
        Claims claims = JwtUtils.checkToken(token);
        String phone = (String) claims.get("phone");

        User user = userMapper.selUser(phone);


        return true;
    }

}
