package com.framework.base.service;

import com.framework.base.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * PROJECT_NAME tcc-base
 * PACKAGE_NAME com.framework.base.service
 * Created by LuoYao
 *
 * @version 1.0
 * @since 2016-05-18  09:58
 * Email:johnny.xiao@live.cn
 */
public class BaseService<T> {

    @Autowired
    protected BaseMapper<T> mapper;

    public T selectOne(T record) {
        return mapper.selectOne(record);
    }

    public List<T> select(T record) {
        return mapper.select(record);
    }

    public List<T> selectAll() {
        return mapper.selectAll();
    }

    public int selectCount(T record) {
        return mapper.selectCount(record);
    }

    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    public int insert(T record) {
        return mapper.insert(record);
    }

    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }

    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    public int delete(T record) {
        return mapper.delete(record);
    }

    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    public List<T> selectByCondition(Object condition) {
        return mapper.selectByCondition(condition);
    }

    public int selectCountByCondition(Object condition) {
        return mapper.selectCountByCondition(condition);
    }

    public int deleteByCondition(Object condition) {
        return mapper.deleteByCondition(condition);
    }

    public int updateByCondition(T record, Object condition) {
        return mapper.updateByCondition(record, condition);
    }

    public int updateByConditionSelective(T record, Object condition) {
        return mapper.updateByConditionSelective(record, condition);
    }
}