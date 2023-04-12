package com.example.springboot_vue.mapper;

import com.example.springboot_vue.pojo.TestUser;
import com.example.springboot_vue.pojo.login.AsyncRouterMap;
import com.example.springboot_vue.pojo.user.RouteAndRole;
import com.example.springboot_vue.pojo.user.User;
import com.example.springboot_vue.pojo.user.UserAndRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserMapper {

    int register(User user);

    User selUser(@Param("username") String phone);

    UserAndRole getRole(int id);

    ArrayList<RouteAndRole> getInfo(int id);

    String[] getRoleFromUrl(String url);

    List<String> getRoles();

    int insertController(@Param("list") List<String> list, @Param("controller") String controller);

    ArrayList<AsyncRouterMap> getRouter();

//    int insertRouter(@Param("map")Map<String, Object> map);

    int insertRouter(AsyncRouterMap asyncRouterMap);

    ArrayList<TestUser> getUser();

}