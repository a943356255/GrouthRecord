package com.example.springboot_vue.security.filter;

import com.example.springboot_vue.pojo.user.User;
import com.example.springboot_vue.security.pojo.JwtUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    SessionRegistry sessionRegistry;
    /**
     * 这里是接受用户输入的账号和密码
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 登录接口，请求方法不是post直接返回异常
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            // form表单输入的登录数据
            Map loginData = new HashMap<>();
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 获取用户名和密码
            String username = (String) loginData.get("username");
            String password = (String) loginData.get("password");
            boolean rememberMe = false;

            if (loginData.get("rememberMe") != null) {
                if (!loginData.get("rememberMe").equals(""))
                    rememberMe = (boolean) loginData.get("rememberMe");
            }

            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();

            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("rememberMe", rememberMe);

            // 生成token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            setDetails(request, authenticationToken);
            JwtUserDetails jwtUserDetails = new JwtUserDetails();

            jwtUserDetails.setUsername(username);
            sessionRegistry.registerNewSession(request.getSession(true).getId(), jwtUserDetails);
            return this.getAuthenticationManager().authenticate(authenticationToken);
        }

        return super.attemptAuthentication(request, response);
    }
}
