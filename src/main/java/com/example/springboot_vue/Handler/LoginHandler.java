package com.example.springboot_vue.Handler;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class LoginHandler implements HandlerInterceptor {

    JwtUtils jwtUtils = new JwtUtils();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String uri = request.getRequestURI();
//        String path = request.getServletPath();
        // 登录，注册以及注册时获取验证码的接口需要放开
//        if (!uri.contains("login") && !uri.contains("register") && !uri.contains("getCode") && !uri.contains("allSearch")
//                && !uri.contains("crudInterface")) {
//            String token = request.getHeader("token");
//            boolean result = jwtUtils.verifyToken(token);
//            if (!result) {
//                throw new TokenVerifyException();
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("post执行完成了");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        String XFor = request.getHeader("X-Forwarded-For");

        String url = request.getRequestURI();

        if (url.equals("/crudInterface/allCRUD")) {

            // 这里读取为空的原因：
            // 接口那里已经用了@ResponseBody注解，它默认就是用流进行读取，而流只能读取一次，再次读取就是空，所以这里获取为空
//            JSONObject map;
//            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
//            StringBuilder responseStrBuilder = new StringBuilder();
//            String inputStr;
//            while ((inputStr = streamReader.readLine()) != null){
//                responseStrBuilder.append(inputStr);
//            }
//            map = JSONObject.parseObject(responseStrBuilder.toString());
//
//            System.out.println(map);
        }

        System.out.println("controller执行完成了");
    }
}
