package com.example.springboot_vue.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.config.RabbitConfig;
import com.example.springboot_vue.controller.crud_interface.ConfirmCallbackService;
import com.example.springboot_vue.controller.crud_interface.ReturnCallbackService;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.mapper.crud.CRUDMapper;
import com.example.springboot_vue.pojo.city.City;
import com.example.springboot_vue.pojo.city.MyMessage;
import com.example.springboot_vue.pojo.city.Paper;
import com.example.springboot_vue.service.CRUDService;
import com.example.springboot_vue.utils.excel_util.EasyExcelDemo;
import com.example.springboot_vue.utils.excel_util.ReadExcel;
import com.rabbitmq.client.Channel;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
//@Transactional
public class CRUDServiceImpl implements CRUDService {

    ThreadLocal<City> threadLocal = new ThreadLocal<>();

    @Autowired
    CRUDMapper crudMapper;

    @Autowired
    CityMapper cityMapper;

//    @Resource
//    MongoTemplate mongoTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private ConfirmCallbackService confirmCallbackService;

    @Autowired
    private ReturnCallbackService returnCallbackService;

    ReentrantLock lock = new ReentrantLock();
    int first = 0;

    InheritableThreadLocal<Map<String, Integer>> inheritableThreadLocal = new InheritableThreadLocal<>();

    @RabbitListener(queues = "DirectQueue")
    @Transactional(rollbackFor = Exception.class)
    public void getMqData(String msg, Channel channel, Message message) throws Exception {
        // 获取到所有的uid
        String uuid = message.getMessageProperties().getHeaders().get("spring_returned_message_correlation").toString();
        int success = cityMapper.getSuccess(uuid);
        // 已经消费过了
        if (success == 1) {
            return;
        }
        HashOperations<String, String, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        String paperId = "测评任务1";
        String[] uid = msg.split(",");
        List<City> cityList = new ArrayList<>();
        Map<String, List<Paper>> allPaper = new HashMap<>();
        for (int i = 0; i < uid.length; i++) {
            List<Paper> paperList = new ArrayList<>();
            for (int j = 0; j < 25; j++) {
                Paper paper = new Paper();
                paper.setA("A");
                paper.setB("B");
                paper.setC("C");
                paper.setD("D");
                paper.setaFile("TM0000001173A.png");
                paper.setbFile("TM0000001173A.png");
                paper.setcFile("TM0000001173A.png");
                paper.setdFile("TM0000001173A.png");
                paper.setTitle("在博物馆参观时同学们看到一尊长了翅膀的怪兽，颜色以黄色和绿色为主，和有名的唐三彩的配色一致，那么据此推测《三彩镇墓兽》应该是中国哪个朝代的艺术品？");
                paperList.add(paper);
            }
            allPaper.put(uid[i], paperList);
            // stringObjectObjectHashOperations.put(paperId, uid[i], paperList);
            City city = new City(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
            city.setMarkId(first++);
            // 出错
            if (city.getMarkId() == 50) {
                city.setMarkId(49);
            }
            cityList.add(city);
        }
        // 将试卷存入数据库
        int val = cityMapper.insertCityAll(cityList);
        int update = cityMapper.updateSuccess(uuid);
        // 改为批量插入
        stringObjectObjectHashOperations.putAll(paperId, allPaper);
//        stringObjectObjectHashOperations.delete(paperId, "uid");
        if (val < 0 || update < 0) {
            throw new Exception();
        }
    }

    @Override
    public void insertRedis(Map<String, Object> map) {
        HashOperations<String, String, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        String paperId = "测评任务1";
        String key = (String) map.get("key");
        List<String> list = (List<String>) stringObjectObjectHashOperations.get(paperId, key);
        System.out.println(list.toString());
        String hashKey = "12345678901111111";
//        for (int i = 0; i < 10000; i++) {
//            List<Paper> list = new ArrayList<>();
//            for (int j = 0; j < 25; j++) {
//                Paper paper = new Paper();
//                paper.setA("A");
//                paper.setB("B");
//                paper.setC("C");
//                paper.setD("D");
//                paper.setaFile("TM0000001173A.png");
//                paper.setbFile("TM0000001173A.png");
//                paper.setcFile("TM0000001173A.png");
//                paper.setdFile("TM0000001173A.png");
//                paper.setTitle("在博物馆参观时同学们看到一尊长了翅膀的怪兽，颜色以黄色和绿色为主，和有名的唐三彩的配色一致，那么据此推测《三彩镇墓兽》应该是中国哪个朝代的艺术品？");
//                list.add(paper);
//            }
//            stringObjectObjectHashOperations.put(paperId, hashKey + i, list);
//        }

//        redisTemplate.opsForValue().set("40032323232442342352352", list);
    }

    @Override
    public JSONObject submitTest(Map<String, Object> map, DataSourceTransactionManager dataSourceTransactionManager) {
        lock.lock();
        try {
            /*
             * 确保消息发送失败后可以重新返回到队列中
             * 注意：yml需要配置 publisher-returns: true
             */
            rabbitTemplate.setMandatory(true);
            // 消费者确认收到消息后，手动ack回执回调处理
            rabbitTemplate.setConfirmCallback(confirmCallbackService);
            // 消息投递到队列失败回调处理
            rabbitTemplate.setReturnsCallback(returnCallbackService);
            int val = confirmCallbackService.mark;
            // 手动开启一个事务
            DefaultTransactionDefinition df = new DefaultTransactionDefinition();
            df.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus transaction = dataSourceTransactionManager.getTransaction(df);

            // 插入数据
            cityMapper.insertTest();
            // message是消息表
            List<MyMessage> messages = new ArrayList<>();
            // 生成10000条数据,list是存储真实的
            List<List<String>> list = new ArrayList<>();
            for (int i = 0; i < 130; i++) {
                // 这里是一批学生的名字
                List<String> res = new ArrayList<>();
                for (int j = 0; j < 1000; j++) {
                    res.add(UUID.randomUUID().toString());
                }

                // 每1000个学生，封装一条消息
                MyMessage message = new MyMessage();
                message.setId(UUID.randomUUID().toString());
                message.setRouteKey("DirectRouting");
                message.setQueueName("DirectQueue");
                message.setExchangeName("DirectExchange");
                message.setStatus(0);
                message.setSuccess(0);
                // 这里封装消息体
                message.setMessageBody(res.toString());
                messages.add(message);
                list.add(res);
            }
            cityMapper.insertMessage(messages);
            dataSourceTransactionManager.commit(transaction);

            // 发送消息
            for (int i = 0; i < messages.size(); i++) {
                CorrelationData correlationData = new CorrelationData(messages.get(i).getId());
                // 将消息携带绑定键值：DirectRouting 发送到交换机DirectExchange
                System.out.println("发送时的id = " + correlationData.getId());
                // list存储的是实际的消息，而message存储的是消息表中的数据，他们两个大小一样，都是130
                rabbitTemplate.convertAndSend("DirectExchange", "DirectRouting", list.get(i), correlationData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return new JSONObject();
    }

    @Override
    public JSONObject getCrudValue(Map<String, Object> map) {

        JSONObject jsonObject = new JSONObject();
        // 获取要操作的表
        String table = (String) map.get("table");
        // 要执行的操作
        String operate = (String) map.get("operate");
        System.out.println("本次操作是：" + operate);

        // 需要操作的字段
        LinkedHashMap<String, Object> columns = (LinkedHashMap) map.get("column");
        System.out.println("columns = " + columns);

        // 需要排序的字段
        // select * from table_name order by column_1, column_2 desc

        // key为要限制的字段，value为具体的限制结果
        LinkedHashMap<String, Object> condition = (LinkedHashMap) map.get("condition");
//        LinkedHashMap<String, Object> columnsArr;
//        if (condition != null) {
//            for (Map.Entry<String, Object> entry : condition.entrySet()) {
//                System.out.println(entry.getKey());
//                columnsArr = (LinkedHashMap<String, Object>) entry.getValue();
//                // 这种写法不行，这样会导致解析玩的map把符号扔掉
//            }
//        }

        // 如果不存在字段，直接返回
        if (columns == null) {
            jsonObject.put("code", -1);
            return jsonObject;
        }

        switch (operate) {
            case "add":
                // 添加数据
                crudMapper.insert(columns, table);
                break;
            case "select":

                int pageNo = (int) map.get("pageNo");
                int pageSize = (int) map.get("pageSize");
                int excelPageSize = 0;
                if (map.get("excelPageSize") != null) {
                    excelPageSize = (int) map.get("excelPageSize");
                }

                LinkedHashMap<String, Object> like = (LinkedHashMap<String, Object>) map.get("like");
                System.out.println("like = " + like);

                String searchValue = (String) map.get("searchValue");

                ArrayList<Map<String, Object>> list;

                // 判断是否是因为导出excel需要查询数据
                if (excelPageSize != 0) {
                    list = crudMapper.select(columns, condition, table, (pageNo - 1) * pageSize, excelPageSize, like, searchValue);
                } else {
                    list = crudMapper.select(columns, condition, table, (pageNo - 1) * pageSize, pageSize, like, searchValue);
                }

                System.out.println("查询结果大小为:" + list.size());
                jsonObject.put("valueList", list);

                int size = crudMapper.selectCount(columns, condition, table, like);
                System.out.println("size大小为" + size);
                jsonObject.put("size", size);

                break;
            case "delete":
                // 删除操作,如果不传条件，默认删除失败
                if (condition == null) {
                    jsonObject.put("code", -1);
                    return jsonObject;
                }
                crudMapper.delete(table, condition);
                break;
            case "update":
                // 修改操作
                crudMapper.update(columns, table, condition);
                break;
            default:
                // 不是上述四种操作，直接返回结果
                jsonObject.put("code", -1);
                return jsonObject;
        }

        return jsonObject;
    }

    @Override
    public void test(Map<String, Object> map) {
        String table = (String) map.get("table");
        List<String> list = (List<String>) map.get("column");

        List<Map<String, String>> res = ReadExcel.readExcelAndInsertIntoDB("D:\\git\\test.xlsx", list);
        crudMapper.insertExcelData(table, res, list);
    }

    @Override
    public void insertCity(String filepath) throws InterruptedException {
        EasyExcelDemo easyExcelDemo = new EasyExcelDemo();

        easyExcelDemo.readExcel(cityMapper, inheritableThreadLocal, filepath, dataSourceTransactionManager);

        //        new Thread(() -> {
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    System.out.println("执行一次");
//                    // 子线程出现问题
//                    if (inheritableThreadLocal.get() != null) {
//                        System.out.println("执行出错误");
//                        if (inheritableThreadLocal.get().get("wrong") != null) {
//                            System.out.println("子线程主线问题");
//                            // 这里执行清除之前插入的代码
//                            List<Map<String, Integer>> map = cityMapper.getRecord("1234", "shtt");
//                            System.out.println(map.toString());
//                        }
//                    }
//                }
//            }, 1000, 1000);
//        }).start();
//        InputStream inputStream = null;
//        try {
//             inputStream = new BufferedInputStream(new FileInputStream("D:\\bilibili_video\\test.xlsx"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        ExcelReader build = EasyExcel.read(inputStream).build();
//        List<ReadSheet> readSheets = build.excelExecutor().sheetList();
//        System.out.println("一共有" + readSheets.size());
//        for (int i = 0; i < readSheets.size(); i++) {
//            int finalI = i;
//            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//                easyExcelDemo.threadReadExcel(cityMapper, readSheets.get(finalI).getSheetName());
//                return null;
//            }, executorService);
//            allFutures.add(future);
//        }
//
//        CompletableFuture<Void> allCompleted = CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0]));
//        allCompleted.join();
    }

    @Override
    public void exportCity(String name, String pageRange, int pageSize, String path) {
        String[] range = pageRange.split("-");
        // 错误的输入范围
        if (range.length > 2) {
            return;
        }

        // 数据大小定义，超过该大小会分页查询
        int dataSize = 30000;
        // 如果数据范围超过dataSize条，则分段读取
        List<int[]> dataRageList = new ArrayList<>();
        // 写入excel的数据总数
        int dataRange = (Integer.parseInt(range[1]) - Integer.parseInt(range[0]));
        // 一共要写入多少个sheet页
        int sheetPage = (int) Math.ceil(dataRange / pageSize);
        // 分多少次读取
        int size = dataRange / dataSize;
        // 是否能够正好读完
        int result = dataRange % dataSize;
        if (size > 0) {
            dataRageList.add(new int[]{Integer.parseInt(range[0]), dataSize});
            for (int i = 1; i < size; i++) {
                int last = dataRageList.get(i - 1)[0];
                dataRageList.add(new int[]{last + dataSize, dataSize});
            }
            if (result != 0) {
                // 最后一次查询的数据范围
                dataRageList.add(new int[]{dataRageList.get(dataRageList.size() - 1)[0] + dataSize, Integer.parseInt(range[1]) - dataRageList.get(dataRageList.size() - 1)[0] - dataSize});
            }
        }
        // 每次写入一页
        ExcelWriter excelWriter = EasyExcel.write(path).build();
        for (int i = 0; i < dataRageList.size(); i++) {
            List<City> list = cityMapper.getPageCity(dataRageList.get(i)[0] - 1, dataRageList.get(i)[1]);
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet_" + i).head(City.class).build();
            excelWriter.write(list, writeSheet);
        }
        excelWriter.finish();
    }

    @Override
    public void insertCityByOneThread(String filepath) {
        EasyExcelDemo easyExcelDemo = new EasyExcelDemo();
        easyExcelDemo.readExcelByOneThread(cityMapper, dataSourceTransactionManager, filepath);
    }

    @Override
    public void insertCityByOneThreadUID(String filepath) {
        EasyExcelDemo easyExcelDemo = new EasyExcelDemo();
        easyExcelDemo.readExcelByOneThreadUID(cityMapper, dataSourceTransactionManager, filepath);
    }

    @Override
    public void insertAutoIdCity(String filepath) {
        EasyExcelDemo easyExcelDemo = new EasyExcelDemo();
        easyExcelDemo.readExcelByAutoId(cityMapper, filepath);
    }
}
