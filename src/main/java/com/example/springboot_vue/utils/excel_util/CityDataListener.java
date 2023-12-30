package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.pojo.city.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CityDataListener implements ReadListener<City> {

    CityMapper cityMapper;

    public CityDataListener(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List<City> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invoke(City city, AnalysisContext analysisContext) {
        cachedDataList.add(city);
        // 一次100条，如果超过100条，就清除之前的内容
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 这里是处理遗留的数据
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    public void saveData() {
        cityMapper.insertCityAll(cachedDataList);
    }
}
