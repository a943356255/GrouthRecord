package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.pojo.city.City;

import java.util.List;
import java.util.Map;

// 单线程插入
public class OtherCityListener implements ReadListener<City> {

    CityMapper cityMapper;

    private static final int BATCH_COUNT = 1000;

    private int index = 0;
    int currentStartNumber = 0;

    private List<City> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public OtherCityListener(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public void invoke(City city, AnalysisContext analysisContext) {
        if (index <= 0) {
            Map<String, Object> map = cityMapper.getTotalData();
            int currentData = (int) map.get("column_count");
            // 获取总行数
            int rowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber() - 1;
            int resultNumber = currentData + rowNumber;
            // 这里的update语句是在主线程中执行的，也就是说这里的事务直到结束是不会提交的。如果在该线程结束之前有其他线程修改，则可能导致死锁
            int res = cityMapper.setData((Integer) map.get("version"), resultNumber);
            while (res == 0) {
                map = cityMapper.getTotalData();
                resultNumber = rowNumber + (int) map.get("column_count");
                res = cityMapper.setData((Integer) map.get("version"), resultNumber);
            }
            index = rowNumber;
            currentStartNumber = (int) map.get("column_count");
        }
        city.setMarkId(++currentStartNumber);
        cachedDataList.add(city);
        index--;
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
            index = 0;
        }
    }

    public int saveData(List<City> cachedDataList) {
        return cityMapper.insertCityAll(cachedDataList);
    }
}
