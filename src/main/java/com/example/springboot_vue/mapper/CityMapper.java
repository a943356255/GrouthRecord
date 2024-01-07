package com.example.springboot_vue.mapper;

import com.example.springboot_vue.pojo.city.City;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CityMapper {

    int insertCity(@Param("areaNames")ArrayList<String> areaName, @Param("parent") String parent);

    int insertCityAndNumber(@Param("nameList") ArrayList<String> name, @Param("numberList") ArrayList<String> number);

    ArrayList<City> selCity();

    int insertCityAll(@Param("list") List<City> list);

    @Select("select count(*) from city")
    int getTotalData();

    List<City> getPageCity(@Param("currentPage") int currentPage, @Param("pageCount") int pageCount);
}
