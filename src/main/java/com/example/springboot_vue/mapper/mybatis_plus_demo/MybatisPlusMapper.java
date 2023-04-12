package com.example.springboot_vue.mapper.mybatis_plus_demo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot_vue.pojo.mybatis_plus_pojo.MybatisPlusPojo;
import org.springframework.stereotype.Repository;

@Repository
public interface MybatisPlusMapper extends BaseMapper<MybatisPlusPojo> {
//
//    /**
//     * 以下定义的 4个 default method, copy from {@link com.baomidou.mybatisplus.extension.toolkit.ChainWrappers}
//     */
//    default QueryChainWrapper<T> queryChain() {
//        return new QueryChainWrapper<>(this);
//    }
//
//    default LambdaQueryChainWrapper<T> lambdaQueryChain() {
//        return new LambdaQueryChainWrapper<>(this);
//    }
//
//    default UpdateChainWrapper<T> updateChain() {
//        return new UpdateChainWrapper<>(this);
//    }
//
//    default LambdaUpdateChainWrapper<T> lambdaUpdateChain() {
//        return new LambdaUpdateChainWrapper<>(this);
//    }
//
//    /**
//     * 以下定义的 4个 method 其中 3 个是内置的选装件
//     */
//    int insertBatchSomeColumn(List<T> entityList);
//
//    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
//
//    int deleteByIdWithFill(T entity);
//
//    /**
//     * 以下为自己自定义
//     */
//    T findOne(Serializable id);
//
//    int deleteAll();
//

}
