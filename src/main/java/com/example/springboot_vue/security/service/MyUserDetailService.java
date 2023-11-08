//package com.example.springboot_vue.security.service;
//
//import com.example.springboot_vue.mapper.UserMapper;
//import com.example.springboot_vue.pojo.user.User;
//import com.example.springboot_vue.pojo.user.UserAndRole;
//import com.example.springboot_vue.security.pojo.JwtUserDetails;
//import com.example.springboot_vue.utils.JwtUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class MyUserDetailService implements UserDetailsService {
//
//    @Autowired
//    UserMapper userMapper;
//
//    /**
//     * 这里是在数据库中查询账号相关的密码
//     * 这里也会将用户所拥有的角色设置在List<GrantedAuthority>，然后返回
//     * 这一步是在登陆时就完成的，用户角色的存储也是在这一步完成
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userMapper.selUser(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("user not exist");
//        }
//        System.out.println("loadUserByUsername的username = " + username);
//
//        // 获取用户角色
//        UserAndRole role = userMapper.getRole(user.getId());
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头 + role.getRole()
//        // 这里先写死为ROLE_ADMIN 这里添加用户的角色，即用户可以访问那些接口
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
//
//        System.out.println("authorities的值为" + authorities);
//
//        JwtUtils jwtUtils = new JwtUtils();
//        // 生成token，并且返回
//        String token = jwtUtils.getToken(user);
//
//        JwtUserDetails jwtUserDetails = new JwtUserDetails(user, authorities, token);
//        jwtUserDetails.setUsername(username);
//
//        return jwtUserDetails;
//    }
//
//
//}
