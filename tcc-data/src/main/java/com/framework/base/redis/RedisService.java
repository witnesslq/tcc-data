package com.framework.base.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.base.jackson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author fengwei //
 * @date 16/4/15 17:15
 */

public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RedisTemplate redisTemplate;

    public Boolean set(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    public String get(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.delete(key);
        return true;
    }

    public Boolean delete(int dbIndex, Set<String> keys) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.delete(keys);
        return true;
    }

    public <T> Boolean set(int dbIndex, String key, T t) {
        boolean result = true;
        try {
            String json = mapper.writeValueAsString(t);
            RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
            redisTemplate.opsForValue().set(key, json);
        } catch (JsonProcessingException e) {
            logger.error("RedisService.set error, key=" + key + " value=" + t, e);
            result = false;
        }

        return result;
    }
    public <T> Boolean multiset(int dbIndex, Map<String, T> map) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (Map.Entry<String, T> entry : map.entrySet()) {
                    String json;
                    try {
                        json = mapper.writeValueAsString(entry.getValue());
                    } catch (JsonProcessingException e) {
                        continue;
                    }
                    connection.set(((RedisSerializer<String>) redisTemplate.getKeySerializer()).serialize(entry.getKey()),
                            ((RedisSerializer<String>) redisTemplate.getValueSerializer()).serialize(json));
                }
                return null;
            }
        });
        return true;
    }

    public <T> T get(int dbIndex, String key, Class<T> clazz) {
        T t = null;
        try {
            RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(value)) {
                return null;
            }

            t = mapper.readValue(value, clazz);
        } catch (Exception e) {
            logger.error("RedisService.get error", e);
        }
        return t;
    }

    public <T> List<T> getList(int dbIndex, String key, Class<T> clazz) {
        List<T> list = null;
        try {
            RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(value)) {
                return null;
            }

            JavaType valueType = mapper.getTypeFactory()
                    .constructParametrizedType(ArrayList.class, List.class, clazz);
            list = mapper.readValue(value, valueType);
        } catch (Exception e) {
            logger.error("RedisService.getList error", e);
        }
        return list;
    }

    public Set<String> scan(int dbIndex, String pattern) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).build();
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                boolean done = false;
                Set<String> keySet = new HashSet<String>();
                // the while-loop below makes sure that we'll get a valid cursor -
                // by looking harder if we don't get a result initially
                while (!done) {
                    Cursor<byte[]> c = connection.scan(scanOptions);
                    try {
                        while (c.hasNext()) {
                            byte[] b = c.next();
                            keySet.add(new String(b));
                        }
                        done = true; //we've made it here, lets go away
                    } catch (NoSuchElementException nse) {
                        logger.error("RedisService.scan error", nse);
                    }
                }
                return keySet;
            }
        });
    }

    public Set<String> scan(int dbIndex, String pattern, long count) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                boolean done = false;
                Set<String> keySet = new HashSet<String>();
                // the while-loop below makes sure that we'll get a valid cursor -
                // by looking harder if we don't get a result initially
                while (!done) {
                    Cursor<byte[]> c = connection.scan(scanOptions);
                    try {
                        while (c.hasNext()) {
                            byte[] b = c.next();
                            keySet.add(new String(b));
                        }
                        done = true; //we've made it here, lets go away
                    } catch (NoSuchElementException nse) {
                        logger.error("RedisService.scan error", nse);
                    }
                }
                return keySet;
            }
        });
    }

    public Boolean incrby(int dbIndex, String key, long delta) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.opsForValue().increment(key, delta);
        return true;
    }

    //发布消息到Channel
    public <T> Boolean convertAndSend(String channel, T message) {
        redisTemplate.convertAndSend(channel, message);
        return true;
    }

    /**
     * TIME
     * Return the current server time
     * @return 时间戳
     */
    public Long time() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.time();
            }
        });
    }

    // ops for list
    /**
     * LPOP key
     * Remove and get the first element in a list
     * @param dbIndex 数据库index
     * @param key 键
     * @return list中的第一个元素
     */
    public String lpop(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * BLPOP key [key ...] timeout
     * Remove and get the first element in a list, or block until one is available
     * @param dbIndex 数据库index
     * @param key 键
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return list中的第一个元素
     */
    public String blpop(int dbIndex, String key, long timeout, TimeUnit timeUnit) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().leftPop(key, timeout, timeUnit);
    }

    /**
     * RPOP key
     * Remove and get the last element in a list
     * @param dbIndex 数据库index
     * @param key 键
     * @return list中最后一个元素
     */
    public String rpop(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * BRPOP key [key ...] timeout
     * Remove and get the last element in a list, or block until one is available
     * @param dbIndex 数据库index
     * @param key 键
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return list中最后一个元素
     */
    public String brpop(int dbIndex, String key, long timeout, TimeUnit timeUnit) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().rightPop(key, timeout, timeUnit);
    }

    /**
     * RPOPLPUSH source destination
     * Remove the last element in a list, prepend it to another list and return it
     * @param dbIndex
     * @param sourceKey
     * @param destinationKey
     * @return
     */
    public String rpoplpush(int dbIndex, String sourceKey, String destinationKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * BRPOPLPUSH source destination timeout
     * Pop a value from a list, push it to another list and return it; or block until one is available
     * @param dbIndex
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param timeUnit
     * @return
     */
    public String brpoplpush(int dbIndex, String sourceKey, String destinationKey, long timeout, TimeUnit timeUnit) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, timeUnit);
    }

    /**
     * LINDEX key index
     * Get an element from a list by its index
     * @param dbIndex
     * @param key
     * @param index
     * @return
     */
    public String lindex(int dbIndex, String key, long index) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * LINSERT key BEFORE|AFTER pivot value
     * Insert an element before or after another element in a list
     * @param dbIndex
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public Long linsert(int dbIndex, String key, String pivot, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * LLEN key
     * Get the length of a list
     * @param dbIndex
     * @param key
     * @return
     */
    public Long llen(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().size(key);
    }

    /**
     * LPUSH key value [value ...]
     * Prepend one or multiple values to a list
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Long lpush(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * LPUSH key value [value ...]
     * Prepend one or multiple values to a list
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Long lpushx(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * LRANGE key start stop
     * Get a range of elements from a list
     * @param dbIndex
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(int dbIndex, String key, long start, long end) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * LREM key count value
     * Remove elements from a list
     * @param dbIndex
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long lrem(int dbIndex, String key, long count, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * LTRIM key start stop
     * Trim a list to the specified range
     * @param dbIndex
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Boolean ltrim(int dbIndex, String key, long start, long end) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.opsForList().trim(key, start, end);
        return true;
    }

    /**
     * LSET key index value
     * Set the value of an element in a list by its index
     * @param dbIndex
     * @param key
     * @param index
     * @param value
     * @return
     */
    public Boolean lset(int dbIndex, String key, long index, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.opsForList().set(key, index, value);
        return true;
    }

    /**
     * RPUSH key value [value ...]
     * Append one or multiple values to a list
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Long rpush(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * RPUSHX key value
     * Append a value to a list, only if the list exists
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Long rpushx(int dbIndex, String key, String value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }


    // ops for hash
    /**
     * HDEL key field [field ...]
     * Delete one or more hash fields
     * @param dbIndex
     * @param key
     * @param hashKeys
     * @return
     */
    public Long hdel(int dbIndex, String key, Object...hashKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * HEXISTS key field
     * Determine if a hash field exists
     * @param dbIndex
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean hexists(int dbIndex, String key, Object hashKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * HGET key field
     * Get the value of a hash field
     * @param dbIndex
     * @param key
     * @param hashKey
     * @return
     */
    public Object hget(int dbIndex, String key, Object hashKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 
     * @param dbIndex
     * @param key
     * @param hashKey
     * @param clazz
     * @return
     */
    public <T> T hget(int dbIndex, String key, Object hashKey, Class<T> clazz) {
        T t = null;
        try {
            RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
            String value = redisTemplate.opsForHash().get(key, hashKey).toString();
            if (StringUtils.isEmpty(value)) {
                return null;
            }

            t = mapper.readValue(value, clazz);
        } catch (Exception e) {
            logger.error("RedisService.get error", e);
        }
        return t;
    }
    /**
     * HGETALL key
     * Get all the fields and values in a hash
     * @param dbIndex
     * @param key
     * @return
     */
    public Map<Object, Object> hgetall(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().entries(key);
    }
	/**
	 * 
	 * @param dbIndex
	 * @param key
	 * @param clazz
	 * @return
	 */
    public <T> List<T> hgetList(int dbIndex, String key, Class<T> clazz) {
        List<T> list = null;
        try {
            RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
            Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
            if(map != null){
    	    	list = new ArrayList<T>();
    	    	for(Object mapKey: map.keySet()){
    	    		String jsonStr = map.get(mapKey).toString();
    	    		if(StringUtils.isNotEmpty(jsonStr)){
    	    			T t = JSONObject.fromObject(jsonStr).getBean(clazz);
    	    			list.add(t);
    	    		}
    	    	}
        	}
        } catch (Exception e) {
            logger.error("RedisService.getList error", e);
        }
        return list;
    }
    /**
     * HINCRBY key field increment
     * Increment the integer value of a hash field by the given number
     * @param dbIndex
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public Long hincrby(int dbIndex, String key, Object hashKey, long delta) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * HINCRBYFLOAT key field increment
     * Increment the float value of a hash field by the given amount
     * @param dbIndex
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public Double hincrbyfloat(int dbIndex, String key, Object hashKey, float delta) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * HKEYS key
     * Get all the fields in a hash
     * @param dbIndex
     * @param key
     * @return
     */
    public Set<Object> hkeys (int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * HLEN key
     * Get the number of fields in a hash
     * @param dbIndex
     * @param key
     * @return
     */
    public Long hlen(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * HMGET key field [field ...]
     * Get the values of all the given hash fields
     * @param dbIndex
     * @param key
     * @param hashKeys
     * @return
     */
    public List<Object> hmget(int dbIndex, String key, Collection<Object> hashKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    /**
     * HMSET key field value [field value ...]
     * Set multiple hash fields to multiple values
     * @param dbIndex
     * @param key
     * @param map
     * @return
     */
    public Boolean hmset(int dbIndex, String key, Map<Object, Object> map) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    /**
     * HSET key field value
     * Set the string value of a hash field
     * @param dbIndex
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public Boolean hset(int dbIndex, String key, Object hashKey, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        redisTemplate.opsForHash().put(key, hashKey, value);
        return true;
    }

    /**
     * HSETNX key field value
     * Set the value of a hash field, only if the field does not exist
     * @param dbIndex
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public Boolean hsetnx(int dbIndex, String key, Object hashKey, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * HVALS key
     * Get all the values in a hash
     * @param dbIndex
     * @param key
     * @return
     */
    public List<Object> hvals(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * HSCAN key cursor [MATCH pattern] [COUNT count]
     * Incrementally iterate hash fields and associated values
     * @param dbIndex
     * @param key
     * @param options
     * @return
     */
    public Cursor<Map.Entry<Object, Object>> hscan(int dbIndex, String key, ScanOptions options) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForHash().scan(key, options);
    }

    //ops for set

    /**
     * SADD key member [member ...]
     * Add one or more members to a set
     * @param dbIndex
     * @param key
     * @param values
     * @return
     */
    public Long sadd(int dbIndex, String key, String...values) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * SCARD key
     * Get the number of members in a set
     * @param dbIndex
     * @param key
     * @return
     */
    public Long scard(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * SDIFF key [key ...]
     * Subtract multiple sets
     * @param dbIndex
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> sdiff(int dbIndex, String key, String otherKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * SDIFF key [key ...]
     * Subtract multiple sets
     * @param dbIndex
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> sdiff(int dbIndex, String key, Collection<String> otherKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * SDIFFSTORE destination key [key ...]
     * Subtract multiple sets and store the resulting set in a key
     * @param dbIndex
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long sdiffstore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * SDIFFSTORE destination key [key ...]
     * Subtract multiple sets and store the resulting set in a key
     * @param dbIndex
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long sdiffstore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * SINTER key [key ...]
     * Intersect multiple sets
     * @param dbIndex
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> sinter(int dbIndex, String key, String otherKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * SINTER key [key ...]
     * Intersect multiple sets
     * @param dbIndex
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> sinter(int dbIndex, String key, Collection<String> otherKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * SINTERSTORE destination key [key ...]
     * Intersect multiple sets and store the resulting set in a key
     * @param dbIndex
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long sinterstore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * SINTERSTORE destination key [key ...]
     * Intersect multiple sets and store the resulting set in a key
     * @param dbIndex
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long sinterstore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * SISMEMBER key member
     * Determine if a given value is a member of a set
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Boolean sismember(int dbIndex, String key, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * SMEMBERS key
     * Get all the members in a set
     * @param dbIndex
     * @param key
     * @return
     */
    public Set<String> smembers(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * SMOVE source destination member
     * Move a member from one set to another
     * @param dbIndex
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public Boolean smove (int dbIndex, String key, String value, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * SPOP key [count]
     * Remove and return one or multiple random members from a set
     * @param dbIndex
     * @param key
     * @return
     */
    public String spop(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * SRANDMEMBER key [count]
     * Get one or multiple random members from a set
     * @param dbIndex
     * @param key
     * @return
     */
    public String srandmember(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * SRANDMEMBER key [count]
     * Get one or multiple random members from a set
     * @param dbIndex
     * @param key
     * @param count
     * @return
     */
    public List<String> srandmember(int dbIndex, String key, long count) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * SREM key member [member ...]
     * Remove one or more members from a set
     * @param dbIndex
     * @param key
     * @param values
     * @return
     */
    public Long srem(int dbIndex, String key, Object...values) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * SUNION key [key ...]
     * Add multiple sets
     * @param dbIndex
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> sunion(int dbIndex, String key, String otherKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * SUNION key [key ...]
     * Add multiple sets
     * @param dbIndex
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> sunion(int dbIndex, String key, Collection<String> otherKeys) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * SUNIONSTORE destination key [key ...]
     * Add multiple sets and store the resulting set in a key
     * @param dbIndex
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long sunionstore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * SUNIONSTORE destination key [key ...]
     * Add multiple sets and store the resulting set in a key
     * @param dbIndex
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long sunionstore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * SSCAN key cursor [MATCH pattern] [COUNT count]
     * Incrementally iterate Set elements
     * @param dbIndex
     * @param key
     * @param options
     * @return
     */
    public Cursor<String> sscan(int dbIndex, String key, ScanOptions options) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForSet().scan(key, options);
    }

    //ops for sorted set

    /**
     * ZADD key [NX|XX] [CH] [INCR] score member [score member ...]
     * Add one or more members to a sorted set, or update its score if it already exists
     * @param dbIndex
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean zadd(int dbIndex, String key, String value, double score) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * ZCARD key
     * Get the number of members in a sorted set
     * @param dbIndex
     * @param key
     * @return
     */
    public Long zcard(int dbIndex, String key) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * ZCOUNT key min max
     * Count the members in a sorted set with scores within the given values
     * @param dbIndex
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(int dbIndex, String key, double min, double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * ZINCRBY key increment member
     * Increment the score of a member in a sorted set
     * @param dbIndex
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public Double zincrby(int dbIndex, String key, String value, double delta) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
     * Intersect multiple sorted sets and store the resulting sorted set in a new key
     * @param dbIndex
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long zinterstore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
     * Intersect multiple sorted sets and store the resulting sorted set in a new key
     * @param dbIndex
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long zinterstore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * ZRANGE key start stop [WITHSCORES]
     * Return a range of members in a sorted set, by index
     * @param dbIndex
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<String> zrange(int dbIndex, String key, long start, long stop) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().range(key, start, stop);
    }

    /**
     * ZRANGEBYLEX key min max [LIMIT offset count]
     * Return a range of members in a sorted set, by lexicographical range
     * @param dbIndex
     * @param key
     * @param range
     * @return
     */
    public Set<String> zrangebylex(int dbIndex, String key, RedisZSetCommands.Range range) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().rangeByLex(key, range);
    }

    /**
     * ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     * Return a range of members in a sorted set, by score
     * @param dbIndex
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrangebysocre(int dbIndex, String key, double min, double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     * Return a range of members in a sorted set, by score
     * @param dbIndex
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<String> zrangebysocre(int dbIndex, String key, double min, double max, int offset, int count) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    /**
     * ZRANK key member
     * Determine the index of a member in a sorted set
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Long zrank(int dbIndex, String key, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * ZREM key member [member ...]
     * Remove one or more members from a sorted set
     * @param dbIndex
     * @param key
     * @param values
     * @return
     */
    public Long zrem(int dbIndex, String key, Object...values) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * ZREMRANGEBYRANK key start stop
     * Remove all members in a sorted set within the given indexes
     * @param dbIndex
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Long zremrangebyrank(int dbIndex, String key, long start, long stop) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().removeRange(key, start, stop);
    }

    /**
     * ZREMRANGEBYSCORE key min max
     * Remove all members in a sorted set within the given scores
     * @param dbIndex
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zremrangebyscore(int dbIndex, String key, double min, double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * ZREVRANGE key start stop [WITHSCORES]
     * Return a range of members in a sorted set, by index, with scores ordered from high to low
     * @param dbIndex
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<String> zrevrange(int dbIndex, String key, long start, long stop) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().reverseRange(key, start, stop);
    }

    /**
     * ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     * Return a range of members in a sorted set, by score, with scores ordered from high to low
     * @param dbIndex
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrevrangebyscore(int dbIndex, String key, double min, double max) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     * Return a range of members in a sorted set, by score, with scores ordered from high to low
     * @param dbIndex
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrevrangebyscore(int dbIndex, String key, double min, double max, int offset, int count) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * ZREVRANK key member
     * Determine the index of a member in a sorted set, with scores ordered from high to low
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Long zrevrank(int dbIndex, String key, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * ZSCORE key member
     * Get the score associated with the given member in a sorted set
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Double zscore(int dbIndex, String key, Object value) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
     * Add multiple sorted sets and store the resulting sorted set in a new key
     * @param dbIndex
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long zunionscore(int dbIndex, String key, String otherKey, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
     * Add multiple sorted sets and store the resulting sorted set in a new key
     * @param dbIndex
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long zunionscore(int dbIndex, String key, Collection<String> otherKeys, String destKey) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }


    /**
     * ZSCAN key cursor [MATCH pattern] [COUNT count]
     * Incrementally iterate sorted sets elements and associated scores
     * @param dbIndex
     * @param key
     * @param options
     * @return
     */
    public Cursor<ZSetOperations.TypedTuple<String>> zscan(int dbIndex, String key, ScanOptions options) {
        RedisTemplate.LOCAL_DB_INDEX.set(dbIndex);
        return redisTemplate.opsForZSet().scan(key, options);
    }


}
