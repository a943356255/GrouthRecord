package com.example.springboot_vue.mapper;

import com.example.springboot_vue.pojo.city.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Select("select column_count, version from version_lock")
    Map<String, Object> getTotalData();

    @Select("select column_count, version from version_lock for update")
    Map<String, Object> getTotalDataForUpdate();

    @Insert("update version_lock set column_count = #{data}, version = version + 1 where version = #{version}")
    int setData(@Param("version") int version, @Param("data") int data);

    List<City> getPageCity(@Param("currentPage") int currentPage, @Param("pageCount") int pageCount);
}
