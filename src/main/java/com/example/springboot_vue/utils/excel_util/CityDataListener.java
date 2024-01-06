package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.pojo.city.City;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class CityDataListener implements ReadListener<City> {

    CityMapper cityMapper;

    public CityDataListener(CityMapper cityMapper, CountDownLatch lunch) {
        this.cityMapper = cityMapper;
    }

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

    @SneakyThrows
    @Override
    public void invoke(City city, AnalysisContext analysisContext) {
        cachedDataList.add(city);
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
        }
        CompletableFuture<Void> allCompleted = CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0]));
        allCompleted.join();
    }

    public int saveData(List<City> cachedDataList) {
        return cityMapper.insertCityAll(cachedDataList);
    }
}
