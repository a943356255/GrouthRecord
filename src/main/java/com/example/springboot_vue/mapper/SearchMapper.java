package com.example.springboot_vue.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.pojo.NewTypeNav;
import com.example.springboot_vue.pojo.goods.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public interface SearchMapper {

    ArrayList<Goods> selAllGoods(@Param("id") int id);

    ArrayList<Type> selAllType();

    int selTypeNum();

    int selAll(@Param("map") Map<String, String> map);

    int insertGoods(Goods goods);

    int insertType(Type type);

    ArrayList<String> selImages(int id);

    ArrayList<Goods> selGoodsById(@Param("map") Map<String, String> map, @Param("start") int start, @Param("end") int end);

    ArrayList<Detail> selDetail(@Param("id") int id);

    ArrayList<NewGoods> selNewGoods(@Param("keyWords") String keyWords, @Param("start") int start, @Param("end") int end);

    KeyToTrademark selNewDetailAndTrademark(@Param("keyWords") String keyWords);

    KeyToTrademark selOneDetail(@Param("detail") String detail);

    ArrayList<NewGoods> selAllDetail(@Param("keyWords") String keyWords, @Param("kvMap") Map<String, Object> map, @Param("start") int start, @Param("end") int end);

//    ArrayList<NewGoods> selAllDetail1(@Param("keyWords") String keyWords, @Param("KVList") ArrayList<Map> KVList);

    @Insert("insert into new_category_table(category1_id, category2_id, category3_id, parent_id) values(\"家用电器\", \"\", \"\", 0)")
    int test();

    ArrayList<NewTypeNav> selTypeNav();

    @Select("select count(*) from goods_table where key_words like concat('%', #{keyWords}, '%')")
    int selCount(String keyWords);

    @Delete("delete from goods_table where id = #{id}")
    int delGoods(int id);

    int addGoods(@Param("map") LinkedHashMap<String, String> hashMap, @Param("detail") JSONObject detailJSON);

    int updGoods(@Param("map") LinkedHashMap<String, String> hashMap, @Param("id") int id);

    int selDeepCount(@Param("keyWords") String keyWords, @Param("kvMap") Map<String, Object> map);
}
