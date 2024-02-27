package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.pojo.city.City;

import java.util.List;

public class AutoIncrementDataListener implements ReadListener<City> {

    CityMapper cityMapper;

    private static final int BATCH_COUNT = 1000;

    private List<City> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public AutoIncrementDataListener(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public void invoke(City city, AnalysisContext analysisContext) {
        cachedDataList.add(city);
        if (cachedDataList.size() > BATCH_COUNT) {
            saveData(cachedDataList);
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            saveData(cachedDataList);
            // 这里如果不进行重置，如果每个sheet不是1000条数据，会存在多插入的情况
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    public int saveData(List<City> cachedDataList) {
        return cityMapper.insertCityAll(cachedDataList);
    }
}
