package com.example.springboot_vue.mapper;

import com.example.springboot_vue.pojo.city.City;
import com.example.springboot_vue.pojo.city.MyMessage;
import com.example.springboot_vue.pojo.city.Paper;
import com.example.springboot_vue.pojo.city.UCity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface CityMapper {

    int insertCity(@Param("areaNames")ArrayList<String> areaName, @Param("parent") String parent);

    int insertCityAndNumber(@Param("nameList") ArrayList<String> name, @Param("numberList") ArrayList<String> number);

    ArrayList<City> selCity();

    int insertCityAll(@Param("list") List<City> list);

    int insertUCityAll(@Param("list") List<UCity> list);

    @Select("select column_count, version from version_lock")
    Map<String, Object> getTotalData();

    @Select("select column_count, version from version_lock for update")
    Map<String, Object> getTotalDataForUpdate();

    @Insert("update version_lock set column_count = #{data}, version = version + 1 where version = #{version}")
    int setData(@Param("version") int version, @Param("data") int data);

    List<City> getPageCity(@Param("currentPage") int currentPage, @Param("pageCount") int pageCount);

    int autoInsertCity(@Param("list") List<City> list);

    @Insert("insert into test(test, test_01) values('test1', 'test1')")
    int insertTest();

    @Insert("insert into test(test, test_01) values(test2, 'test2')")
    int insertTest1();

    @Select("select test, test_01 from test")
    Map<String, Object> getAllData();

    int insertPaper(List<Paper> list);

    int insertMessage(List<MyMessage> list);

    @Update("update message_table set status = 1 where id = #{id}")
    int update(String id);

    @Update("update message_table set success = 1 where id = #{id}")
    int updateSuccess(String id);

    @Select("select success from message_table where id = #{id}")
    int getSuccess(String id);

    @Insert("insert into mark_table(start, end, user, type) values(#{start}, #{end}, #{user}, #{type})")
    int insertId(int start, int end, String user, String type);

    @Select("select start, end from mark_table where user = #{user} and type = #{type}")
    List<Map<String, Integer>> getRecord(String user, String type);

    int deleteRecord(Map<String, Integer> map);
}
