package com.example.springboot_vue.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.SearchMapper;
import com.example.springboot_vue.pojo.NewTypeNav;
import com.example.springboot_vue.pojo.goods.*;
import com.example.springboot_vue.pojo.type_nav.FirstLevel;
import com.example.springboot_vue.pojo.type_nav.SecondLevel;
import com.example.springboot_vue.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchMapper searchMapper;

    @Override
    public ReturnType getReturnType(Map<String, String> map) {
        ReturnType returnType = new ReturnType();

        // 封装page相关的属性，如果pageNo和pageSize都为空，则采用默认值，第一页，以及每页十条数据
        int pageNo = 1, pageSize = 10;
        if (map.get("pageNo") != null) {
            pageNo = Integer.parseInt(map.get("pageNo"));
        }
        returnType.setPageNo(pageNo);
        if (map.get("pageSize") != null) {
            pageSize = Integer.parseInt(map.get("pageSize"));
        }
        returnType.setPageSize(pageSize);

        // 根据条件进行查找，start和end是当前页面需要的数据范围
        int start = (pageNo - 1) * pageSize;
        int end = pageSize;
        ArrayList<Goods> goodsList = searchMapper.selGoodsById(map, start, end);
        returnType.setGoodsList(goodsList);

        // 计算页数，这里需要查找到所有相关的数据，然后再计算页面
        int all = searchMapper.selAll(map);
        returnType.setTotal(all);
        int totalPage = all % returnType.getPageSize() == 0 ? all / returnType.getPageSize() : all / returnType.getPageSize() + 1;
        returnType.setTotalPage(totalPage);

        // 对attrsList进行封装
        int type = searchMapper.selTypeNum();
        ArrayList<AttrsList> attrsList = new ArrayList<>();
        ArrayList<Type> typeList = searchMapper.selAllType();
        for (int i = 0; i < type; i++) {
            AttrsList list = new AttrsList();
            ArrayList<String> attrList = new ArrayList<>();
            list.setId(i);
            list.setAttrsList(attrList);
            attrsList.add(list);
        }
        for (int i = 0; i < typeList.size(); i++) {
            // 获取到id，这个id是用来分类，例如id = 1代表网络格式，id = 2代表价格
            int id = typeList.get(i).getCategoryId();
            // 添加属性名称
            attrsList.get(id).getAttrsList().add(typeList.get(i).getName());
            // 设置title
            if (attrsList.get(id).getTitle() == null) {
                attrsList.get(id).setTitle(typeList.get(i).getTypeTitle());
            }
        }
        returnType.setAttrsList(attrsList);

        // 封装trademark数据
        ArrayList<String> trademarkList = searchMapper.selImages(1);
        returnType.setTrademarkList(trademarkList);

        return returnType;
    }

    @Override
    public ArrayList<Goods> selAllGoods() {
        // 这里查询全部商品，所以id传-1
        return searchMapper.selAllGoods(-1);
    }

    @Override
    public ArrayList<Type> selAllType() {
        return searchMapper.selAllType();
    }

    @Override
    public JSONObject getDetail(String id) {
        JSONObject jsonObject = new JSONObject();
        int detailId = Integer.parseInt(id);

        // 获取商品信息，传入id的话只会返回一个商品，即list.size()=1
        ArrayList<Goods> list = searchMapper.selAllGoods(detailId);
        // 获取该商品的详细属性
        ArrayList<Detail> details = searchMapper.selDetail(detailId);

        String[] key = new String[details.size()];
        ArrayList<ArrayList<String>> value = new ArrayList<>();
        ArrayList<ArrayList<Integer>> priceList = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            // 遍历json，拿到key以及value，这里是对应商品的所有详细种类
            for (Map.Entry<String, Object> entry : details.get(i).getAttrJson().entrySet()) {
                key[i] = entry.getKey();
                ArrayList<String> valueList = (ArrayList<String>) entry.getValue();
                value.add(valueList);
            }
            // 分割price数组，取出每一个种类对应的价格
            String[] prices = details.get(i).getPrice().split(":");
            ArrayList<Integer> list1 = new ArrayList<>();
            for (int k = 0; k < prices.length; k++) {
                list1.add(Integer.valueOf(prices[k]));
            }
            priceList.add(list1);
        }
        jsonObject.put("keyList", key);
        jsonObject.put("valueList", value);
        jsonObject.put("goods", list.get(0));
        jsonObject.put("priceList", priceList);

        return jsonObject;
    }

    @Override
    public JSONObject getSearchResult(Map<String, String> map) {

        JSONObject jsonObject = new JSONObject();
        String keyWords = map.get("keyWords");

        // 计算页数
        int start = 1, end = 10;
        String pageNo = map.get("pageNo");
        String pageSize = map.get("pageSize");
        if (pageNo != null) {
            start = Integer.parseInt(pageNo);
        }
        if (pageSize != null) {
            end = Integer.parseInt(pageSize);
        }

        // 查询跟keyWords相关的展品进行展示，默认从第一页开始
        ArrayList<NewGoods> list = searchMapper.selNewGoods(keyWords, (start - 1) * end, end);
        // 设置数据条数，用于前端分页
        int size = searchMapper.selCount(keyWords);
//        jsonObject.put("goodsList", list);
//        jsonObject.put("size", size);
//
//        // 查询跟keyWords相关的一些品牌以及detail，应该就一条数据
//        KeyToTrademark trademarksAndDetail = searchMapper.selNewDetailAndTrademark(keyWords);
//        // 没有数据，直接返回
//        if (trademarksAndDetail == null) {
//            jsonObject.put("code", 200);
//            return jsonObject;
//        }
//
//        // 获取trademarkKey
//        String trademarkKey = null;
//        for (Map.Entry<String, Object> entry : trademarksAndDetail.getTrademark().entrySet()) {
//            trademarkKey = entry.getKey();
//        }
//        ArrayList<String> trademarks = (ArrayList<String>) trademarksAndDetail.getTrademark().get(trademarkKey);

        // 获取detailKey，默认只有4个分类显示
//        String[] detailsKey = new String[trademarksAndDetail.getDetail().size()];
//        ArrayList<ArrayList<String>> details = new ArrayList<>();
//        int index = 0;
//        for (Map.Entry<String, Object> entry : trademarksAndDetail.getDetail().entrySet()) {
//            // 记录key
//            detailsKey[index] = entry.getKey();
//            // 记录value
//            ArrayList<String> arr = (ArrayList<String>) trademarksAndDetail.getDetail().get(detailsKey[index]);
//            details.add(arr);
//            index++;
//        }
//        jsonObject.put("detailKey", detailsKey);
//        jsonObject.put("detailList", details);

//        jsonObject.put("jsonObj", trademarksAndDetail);
//        jsonObject.put("trademarkList", trademarks);

        jsonObject.put("code", 200);
        return jsonObject;
    }

    @Override
    public JSONObject deepSearch(Map<String, Object> map) {

        JSONObject jsonObject = new JSONObject();

        // 接收参数
        String keyWords = (String) map.get("keyWords");
        String stringValues = (String) map.get("map");
        JSONObject keyAndValues = JSONObject.parseObject(stringValues);

        System.out.println(map.get("pageNo"));
        System.out.println(map.get("pageSize"));
        // 计算页数
        int pageNo, pageSize;
        try {
            pageNo = (int) map.get("pageNo");
            pageSize = (int) map.get("pageSize");
        } catch (NullPointerException e) {
            pageNo = 1;
            pageSize = 2;
        }

        // 用于存储处理后的参数
        Map<String, Object> kvMap = new HashMap<>();
        if (keyAndValues != null) {
            for (Map.Entry<String, Object> entry : keyAndValues.entrySet()) {
                // 获取到key
                String key = entry.getKey();
                // 获取到value
                JSONArray value = (JSONArray) keyAndValues.get(key);
                if (value.size() == 0) {
                    continue;
                }
//            if (value.size() != 0) {
//                keys.add(key);
//            } else  {
//                continue;
//            }
                ArrayList<String> valueList = new ArrayList<>();
                for (Object o : value) {
                    valueList.add((String) o);
                }
                kvMap.put(key, valueList);
            }
        }

        ArrayList<NewGoods> list = searchMapper.selAllDetail(keyWords, kvMap,(pageNo - 1) * pageSize, pageSize);
//        ArrayList<Map> KVList = new ArrayList<>();
//
//        keys.forEach(key -> {
//            Map tempMap = new HashMap();
//            tempMap.put("key", key);
//            KVList.add(tempMap);
//        });
//
//        for (int i = 0; i < values.size(); i++) {
//            Map tempMap = KVList.get(i);
//            tempMap.put("value", values.get(i));
//        }
//
//        ArrayList<NewGoods> list = searchMapper.selAllDetail1(keyWords, KVList);
//        ArrayList<NewGoods> list = searchMapper.selAllDetail(keyWords, keys, values);

//        jsonObject.put("resultList", list);

        int size = searchMapper.selDeepCount(keyWords, kvMap);
        jsonObject.put("goodsList", list);
        jsonObject.put("size", size);

        // 查询跟keyWords相关的一些品牌以及detail，应该就一条数据
        KeyToTrademark trademarksAndDetail = searchMapper.selNewDetailAndTrademark(keyWords);
        // 没有数据，直接返回
        if (trademarksAndDetail == null) {
            jsonObject.put("code", 200);
            return jsonObject;
        }

        // 获取trademarkKey
        String trademarkKey = null;
        for (Map.Entry<String, Object> entry : trademarksAndDetail.getTrademark().entrySet()) {
            trademarkKey = entry.getKey();
        }
        ArrayList<String> trademarks = (ArrayList<String>) trademarksAndDetail.getTrademark().get(trademarkKey);

        jsonObject.put("jsonObj", trademarksAndDetail);
        jsonObject.put("trademarkList", trademarks);
        jsonObject.put("code", 200);
        return jsonObject;
    }

    @Override
    public JSONObject getTypeNav() {
        JSONObject jsonObject = new JSONObject();

        ArrayList<NewTypeNav> list = searchMapper.selTypeNav();
        // 用于标记各种类的数量

        ArrayList<FirstLevel> levelFirst = new ArrayList<>();
        ArrayList<SecondLevel> levelSecond = new ArrayList<>();

        int firstIndex = 0, secondIndex = 0;
        Map<Integer, Integer> firstMap = new HashMap<>();
        Map<Integer, Integer> secondMap = new HashMap<>();

        // 第一遍遍历搜素结果，封装最外层以及第二层的数据
        for (NewTypeNav newTypeNav : list) {
            if (newTypeNav.getParentId() == 0) {
                // 封装数据
                FirstLevel firstLevel = new FirstLevel();
                firstLevel.setCategoryId(newTypeNav.getCategory1Id());
                firstLevel.setId(newTypeNav.getId());
                // 设置初始化的集合
                ArrayList<SecondLevel> secondLevels = new ArrayList<>();
                firstLevel.setFirstLevelList(secondLevels);

                levelFirst.add(firstLevel);
                // key为该条记录的id， value存储它在list中的下标
                firstMap.put(newTypeNav.getId(), firstIndex);
                firstIndex++;
            } else if (newTypeNav.getParentId() != 0 && newTypeNav.getParent2Id() == 0) {
                // 封装基本数据
                SecondLevel secondLevel = new SecondLevel();
                secondLevel.setCategory2Id(newTypeNav.getCategory2Id());
                secondLevel.setId(newTypeNav.getId());
                ArrayList<String> arrayList = new ArrayList<>();
                secondLevel.setLevelThreeList(arrayList);

                levelSecond.add(secondLevel);
                secondMap.put(newTypeNav.getId(), secondIndex);
                secondIndex++;
            }
        }

        for (NewTypeNav newTypeNav : list) {
            // parent2Id是该分类二级父类id，如果包含该key，可以取value，就是其父类在list中的下标
            if (secondMap.containsKey(newTypeNav.getParent2Id())) {
                int index = secondMap.get(newTypeNav.getParent2Id());
                levelSecond.get(index).getLevelThreeList().add(newTypeNav.getCategory3Id());
            }
        }

        // 遍历封装最外层
        for (NewTypeNav newTypeNav : list) {
            // 由于三级分类的parentId也为一级分类id，所以多判断以下parent2Id的值
            if (firstMap.containsKey(newTypeNav.getParentId()) && newTypeNav.getParent2Id() == 0) {
                int index = firstMap.get(newTypeNav.getParentId());
                SecondLevel secondLevel = levelSecond.get(secondMap.get(newTypeNav.getId()));
                levelFirst.get(index).getFirstLevelList().add(secondLevel);
            }
        }

        jsonObject.put("list", levelFirst);
        jsonObject.put("code", 200);
        return jsonObject;
    }

    @Override
    public JSONObject addGoods(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();

        LinkedHashMap<String, String> hashMap = (LinkedHashMap<String, String>) map.get("form");
        // 字符串转化为json
        JSONObject detailJSON = JSONObject.parseObject((String) map.get("map"));

        searchMapper.addGoods(hashMap, detailJSON);

        return jsonObject;
    }

    @Override
    public JSONObject getNewDetail(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();

        String keyWords = (String) map.get("keyWords");

        KeyToTrademark trademarksAndDetail = searchMapper.selNewDetailAndTrademark(keyWords);
        String[] detailsKey = new String[trademarksAndDetail.getDetail().size()];
        ArrayList<ArrayList<String>> details = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Object> entry : trademarksAndDetail.getDetail().entrySet()) {
            // 记录key
            detailsKey[index] = entry.getKey();
            // 记录value
            ArrayList<String> arr = (ArrayList<String>) trademarksAndDetail.getDetail().get(detailsKey[index]);
            details.add(arr);
            index++;
        }
        jsonObject.put("detailKey", detailsKey);
        jsonObject.put("detailList", details);

        return jsonObject;
    }

    @Override
    public JSONObject delGoods(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();

        searchMapper.delGoods(((Integer) map.get("id")));
        return jsonObject;
    }

    @Override
    public JSONObject changeGoods(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();

        // 获取到修改的属性
        LinkedHashMap<String, String> hashMap = (LinkedHashMap<String, String>) map.get("form");
        searchMapper.updGoods(hashMap, (Integer) map.get("id"));

        return jsonObject;
    }
}
