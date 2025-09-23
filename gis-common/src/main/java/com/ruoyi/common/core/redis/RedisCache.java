package com.ruoyi.common.core.redis;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisCache
{
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    @Autowired(required = false)
    public RedisTemplate redisTemplate;
    
    @Autowired
    private RedisProperties redisProperties;

    // 本地缓存实现
    private final Map<String, Object> localCache = new ConcurrentHashMap<>();
    private final Map<String, Long> expireTimes = new ConcurrentHashMap<>();

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                redisTemplate.opsForValue().set(key, value);
                return;
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        localCache.put(key, value);
        expireTimes.remove(key); // 清除过期时间
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
                return;
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        localCache.put(key, value);
        long expireTime = System.currentTimeMillis() + timeUnit.toMillis(timeout);
        expireTimes.put(key, expireTime);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.expire(key, timeout, unit);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        if (localCache.containsKey(key)) {
            long expireTime = System.currentTimeMillis() + unit.toMillis(timeout);
            expireTimes.put(key, expireTime);
            return true;
        }
        return false;
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.getExpire(key);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        if (expireTimes.containsKey(key)) {
            long expireTime = expireTimes.get(key);
            long now = System.currentTimeMillis();
            if (expireTime > now) {
                return (expireTime - now) / 1000; // 返回秒数
            } else {
                localCache.remove(key);
                expireTimes.remove(key);
            }
        }
        return -2; // 本地缓存中键不存在
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.hasKey(key);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        checkAndRemoveExpired(key);
        return localCache.containsKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                ValueOperations<String, T> operation = redisTemplate.opsForValue();
                return operation.get(key);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        checkAndRemoveExpired(key);
        return (T) localCache.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.delete(key);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        Object removed = localCache.remove(key);
        expireTimes.remove(key);
        return removed != null;
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection collection)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.delete(collection) > 0;
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        boolean result = false;
        for (Object key : collection) {
            if (key instanceof String) {
                Object removed = localCache.remove(key);
                expireTimes.remove(key);
                if (removed != null) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
                return count == null ? 0 : count;
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        localCache.put(key, new CopyOnWriteArrayList<>(dataList));
        expireTimes.remove(key); // 清除过期时间
        return dataList.size();
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.opsForList().range(key, 0, -1);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        checkAndRemoveExpired(key);
        Object value = localCache.get(key);
        if (value instanceof List) {
            return (List<T>) value;
        }
        return null;
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
                Iterator<T> it = dataSet.iterator();
                while (it.hasNext())
                {
                    setOperation.add(it.next());
                }
                return setOperation;
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        localCache.put(key, new CopyOnWriteArraySet<>(dataSet));
        expireTimes.remove(key); // 清除过期时间
        return null; // 本地缓存不支持BoundSetOperations
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.opsForSet().members(key);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        checkAndRemoveExpired(key);
        Object value = localCache.get(key);
        if (value instanceof Set) {
            return (Set<T>) value;
        }
        return null;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                if (dataMap != null) {
                    redisTemplate.opsForHash().putAll(key, dataMap);
                }
                return;
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        if (dataMap != null) {
            localCache.put(key, new ConcurrentHashMap<>(dataMap));
            expireTimes.remove(key); // 清除过期时间
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.opsForHash().entries(key);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        checkAndRemoveExpired(key);
        Object value = localCache.get(key);
        if (value instanceof Map) {
            return (Map<String, T>) value;
        }
        return null;
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                redisTemplate.opsForHash().put(key, hKey, value);
                return;
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        Object mapObj = localCache.get(key);
        if (mapObj instanceof Map) {
            ((Map<String, Object>) mapObj).put(hKey, value);
        } else {
            // 如果key不存在或者不是Map类型，则创建新的Map
            Map<String, Object> newMap = new ConcurrentHashMap<>();
            newMap.put(hKey, value);
            localCache.put(key, newMap);
        }
        expireTimes.remove(key); // 清除过期时间
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
                return opsForHash.get(key, hKey);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        checkAndRemoveExpired(key);
        Object mapObj = localCache.get(key);
        if (mapObj instanceof Map) {
            return (T) ((Map<String, Object>) mapObj).get(hKey);
        }
        return null;
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.opsForHash().multiGet(key, hKeys);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        checkAndRemoveExpired(key);
        Object mapObj = localCache.get(key);
        if (mapObj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) mapObj;
            return hKeys.stream().map(k -> (T) map.get(k.toString())).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.opsForHash().delete(key, hKey) > 0;
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        Object mapObj = localCache.get(key);
        if (mapObj instanceof Map) {
            Object removed = ((Map<String, Object>) mapObj).remove(hKey);
            return removed != null;
        }
        return false;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        if (redisProperties.isEnabled() && redisTemplate != null) {
            try {
                return redisTemplate.keys(pattern);
            } catch (Exception e) {
                logger.warn("Redis operation failed, fallback to local cache", e);
            }
        }
        // 使用本地缓存
        // 简单实现 - 实际项目中可能需要更复杂的模式匹配
        return localCache.keySet().stream()
                .filter(key -> key.startsWith(pattern.replace("*", "")))
                .collect(Collectors.toList());
    }

    /**
     * 检查并移除过期的键
     * @param key 键
     */
    private void checkAndRemoveExpired(String key) {
        if (expireTimes.containsKey(key)) {
            long expireTime = expireTimes.get(key);
            if (System.currentTimeMillis() > expireTime) {
                localCache.remove(key);
                expireTimes.remove(key);
            }
        }
    }
    
    /**
     * 定时清理过期的本地缓存数据，避免内存溢出
     * 每30分钟执行一次清理
     */
    @Scheduled(fixedRate = 30 * 60 * 1000, initialDelay = 60 * 1000)
    public void cleanExpiredLocalCache() {
        // 只有在Redis禁用时才需要清理本地缓存
        if (redisProperties.isEnabled()) {
            return;
        }
        
        logger.debug("开始清理过期的本地缓存数据");
        long countBefore = localCache.size();
        
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, Long>> iterator = expireTimes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            if (currentTime > entry.getValue()) {
                localCache.remove(entry.getKey());
                iterator.remove();
            }
        }
        
        long countAfter = localCache.size();
        logger.debug("本地缓存清理完成，清理前数量: {}，清理后数量: {}，清理数量: {}", 
                countBefore, countAfter, (countBefore - countAfter));
    }
}