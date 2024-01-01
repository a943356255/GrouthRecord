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

    public CityDataListener(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;

    /**
     * 缓存的数据
     */
    private List<City> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 创建一个线程池
     */
    ExecutorService executor = Executors.newFixedThreadPool(10);

    ExecutorService executorService = new ThreadPoolExecutor(10, 20, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10));

    List<List<City>> wrongList = new ArrayList<>();

    @SneakyThrows
    @Override
    public void invoke(City city, AnalysisContext analysisContext) {
        cachedDataList.add(city);
        // 一次1000条，如果超过1000条，就清除之前的内容
        if (cachedDataList.size() >= BATCH_COUNT) {
//            saveData(cachedDataList);
            List<City> tempList = new ArrayList<>(cachedDataList);
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            // 线程池执行
//            executor.submit(() -> saveData(tempList));

            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> saveData(tempList), executor);
            future.thenAccept(System.out::println).exceptionally(ex -> {
                wrongList.add(tempList);
                return null;
            });

            // 如果直接获取get，就变成了串行处理
//            try {
//                int res = executor.submit(() -> saveData(tempList)).get();
//                System.out.println(res + "" + wrongList.size());
//            } catch (Exception e) {
//                wrongList.add(tempList);
//            }
        }
    }

    /**
     * 这里是处理遗留的数据
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        saveData();
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            saveData(cachedDataList);
        }
    }

    public int saveData(List<City> cachedDataList) {
        return cityMapper.insertCityAll(cachedDataList);
    }
}
