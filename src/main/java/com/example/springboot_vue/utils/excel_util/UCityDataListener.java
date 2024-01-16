package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.pojo.city.UCity;

import java.util.List;
import java.util.UUID;

public class UCityDataListener implements ReadListener<UCity> {

    CityMapper cityMapper;

    private static final int BATCH_COUNT = 1000;

    private List<UCity> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public UCityDataListener(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public void invoke(UCity city, AnalysisContext analysisContext) {
        city.setMarkId(UUID.randomUUID().toString());
        cachedDataList.add(city);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData(cachedDataList);
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            saveData(cachedDataList);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    public int saveData(List<UCity> cachedDataList) {
        return cityMapper.insertUCityAll(cachedDataList);
    }

}
