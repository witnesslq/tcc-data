package com.framework.base.mapper;

import org.slf4j.Marker;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.SqlServerMapper;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;

/**
 * PROJECT_NAME tcc-base
 * PACKAGE_NAME com.framework.base.mapper
 * Created by LuoYao
 *
 * @version 1.0
 * @since 2016-05-18  09:59
 * Email:johnny.xiao@live.cn
 */
public interface BaseMapper<T> extends BaseSelectMapper<T>, BaseUpdateMapper<T>, BaseDeleteMapper<T>, SqlServerMapper<T>,
        ConditionMapper<T>, RowBoundsMapper<T>, Marker {

}
