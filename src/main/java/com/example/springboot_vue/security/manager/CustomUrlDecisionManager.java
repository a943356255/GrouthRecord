//package com.example.springboot_vue.security.manager;
//
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//
//@Component
//public class CustomUrlDecisionManager implements AccessDecisionManager {
//
//    /**
//     * 2、走完1（CustomFilterInvocationSecurityMetadataSource）之后走这里
//     * 这一步会拿到第一步创建的角色集合（访问接口所需要的权限），然后进行遍历对比
//     */
//    @Override
//    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//
//        System.out.println("configAttributes = " + configAttributes);
//        for (ConfigAttribute configAttribute : configAttributes) {
//            // 这里是接口所需要的权限
//            String needRole = configAttribute.getAttribute();
//            System.out.println("needRole = " + needRole);
//            if ("ROLE_LOGIN".equals(needRole)) {
//                if (authentication instanceof AnonymousAuthenticationToken) {
//                    throw new AccessDeniedException("尚未登录，请登录!");
//                } else {
//                    return;
//                }
//            }
//
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            System.out.println("用户所拥有的权限authorities = " + authorities);
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(needRole)) {
//                    System.out.println("在这里进行了返回");
//                    return;
//                }
//            }
//        }
//
//        throw new AccessDeniedException("权限不足，请联系管理员!");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute attribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}
