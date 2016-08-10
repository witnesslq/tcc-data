package com.junit.mvc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created with IntelliJ IDEA.
 * JUnit测试action时使用的基类
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-9 21:17
 * Email:johnny_lx@yahoo.com
 */
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext-core.xml"})
public class JUnitActionBase extends AbstractJUnit4SpringContextTests {

    @Autowired
    protected StringRedisTemplate template;

    @Autowired
    protected RedisTemplate redisTemplate;

    public  String getKey(String key){
        return template.opsForValue().get(key);
    }

    /**
     * 按key删除
     * @param key
     */
    public void delKey(String key){
         template.delete(key);

    }

    /**
     * 修改key
     * @param key
     * @param newKey
     */
    public void updateKey(String key,String newKey){
        template.rename(key,newKey);
    }


    @Test
    public void test(){
        String x= getKey("123");
        System.out.println("redis key is 123  -------"+x);

        delKey("123");
        System.out.println("redis key is 123  -------"+getKey("123")+"---------"+redisTemplate);
    }
}
