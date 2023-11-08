//package com.example.springboot_vue.security.manager;
//
//import com.example.springboot_vue.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import java.util.Collection;
//
//@Component
//public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//
//    AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//    @Autowired
//    UserMapper userMapper;
//
//    /**
//     * 这里应该是返回该接口需要那些权限才可以访问，返回一个接口需要权限的list
//     * 1、先走这个方法，创建该接口所需要的权限（用户所拥有的角色在登陆时已经记录）
//     */
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        // 这个url是用户访问的url
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//        System.out.println("url = " + requestUrl);
//
//        // 1、查询该url所需要的角色。
//        String[] roles = userMapper.getRoleFromUrl(requestUrl);
//
//        // 2、将所有的角色用SecurityConfig.createList(roles)返回即可。
//        if (roles.length == 0) {
//            return SecurityConfig.createList("ROLE_LOGIN");
//        } else {
//            for (int i = 0; i < roles.length; i++) {
//                roles[i] = "ROLE_" + roles[i];
//            }
//            return SecurityConfig.createList(roles);
//        }
//        // 注解拿权限
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}
