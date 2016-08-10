package com.framework.base.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-12 16:54
 * Email:johnny_lx@yahoo.com
 */

public abstract class AbstractRedisDaoBase<k,v> {

    @Autowired
    protected RedisTemplate redisTemplate;


    public void setRedisTemplate(RedisTemplate<k,v> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    protected RedisSerializer<String> getRedisSerializer(){
        return redisTemplate.getStringSerializer();
    }
}
