package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.pojo.city.City;
import com.example.springboot_vue.rabbitmq_test.RabbitMQProvider;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCityDataListener implements ReadListener<City> {

    CityMapper cityMapper;

    private int index = 0;

    Lock lock = new ReentrantLock(true);

    RabbitMQProvider rabbitMQProvider = new RabbitMQProvider();

    int currentStartNumber = 0;
    /**
     * 这里是设置批量插入数据的大小
     */
    private static final int BATCH_COUNT = 1000;

    /**
     * 缓存的数据
     */
    private List<City> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 创建一个线程池
     */
    ExecutorService executorService = new ThreadPoolExecutor(20, 40, 10, TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    List<List<City>> wrongList = new ArrayList<>();

    List<CompletableFuture<Integer>> allFutures = new ArrayList<>();

    public LockCityDataListener(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @SneakyThrows
    @Override
    public void invoke(City city, AnalysisContext analysisContext) {
        if (index <= 0) {
            Map<String, Object> map = cityMapper.getTotalData();
            int currentData = (int) map.get("column_count");
            // 获取总行数
            int rowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber() - 1;
            int resultNumber = currentData + rowNumber;
            int res = cityMapper.setData((Integer) map.get("version"), resultNumber);
//            rabbitMQProvider.sendMessage("当前res的大小为:" + res);
            while (res == 0) {
                map = cityMapper.getTotalData();
                resultNumber = rowNumber + (int) map.get("column_count");
                res = cityMapper.setData((Integer) map.get("version"), resultNumber);
                rabbitMQProvider.sendMessage("出错了:(");
            }
            index = rowNumber;
            currentStartNumber = (int) map.get("column_count");
//            rabbitMQProvider.sendMessage("当前sheet的大小为:" + index);
        }
        city.setMarkId(++currentStartNumber);
        cachedDataList.add(city);
        index--;
        // 一次1000条，如果超过1000条，就清除之前的内容
        if (cachedDataList.size() >= BATCH_COUNT) {
            List<City> tempList = new ArrayList<>(cachedDataList);
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> saveData(tempList), executorService)
                    .exceptionally(ex -> {
                        wrongList.add(tempList);
                        return null;
                    });
            allFutures.add(future);
        }
    }

    /**
     * 这里是处理遗留的数据
     * 注意：这里是每一个sheet读取完成后都会触发一次
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            saveData(cachedDataList);
            // 这里如果不进行重置，如果每个sheet不是1000条数据，会存在多插入的情况
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            index = 0;
        }
        CompletableFuture<Void> allCompleted = CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0]));
        allCompleted.join();
        System.out.println("wrongList的大小为" + wrongList.size());
    }

    public int saveData(List<City> cachedDataList) {
        return cityMapper.insertCityAll(cachedDataList);
    }

}
