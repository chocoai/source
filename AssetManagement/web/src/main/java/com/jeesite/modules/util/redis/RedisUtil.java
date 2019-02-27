package com.jeesite.modules.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisUtil<K, V> extends RedisTemplate<K, V> {
    public static final int DEFAULT =0;     //Default OR User
    public static final int DEPT = 1;
    public static final int DINGUSER = 2;

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    //@PostConstruct
    //public void init(){
    //    redisTemplate = new RedisUtil<>(getJedisConnectionFactory());
    //}

    @Deprecated
    public JedisConnectionFactory getJedisConnectionFactory() {
        if(jedisConnectionFactory == null) {
            jedisConnectionFactory =(JedisConnectionFactory)redisTemplate.getConnectionFactory();
        }
        return jedisConnectionFactory;
    }

    public void setDatabase(int db){
        //RedisConnectionFactory c = redisTemplate.getConnectionFactory();
        //JedisConnectionFactory jedisConnectionFactory =  (JedisConnectionFactory)redisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(db);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
    }
    public void set(int db,K k,V v){ set(db, k,v, null); }
    public void set(int db,K k,V v, Long microSeconds){
        set(db, k, v, microSeconds, TimeUnit.MICROSECONDS);
    }
    public void set(int db,K k,V v, Long timeOut, final TimeUnit timeUnit){
        setDatabase(db);
        if(timeOut == null) redisTemplate.opsForValue().set(k,v);
        else redisTemplate.opsForValue().set(k,v, timeOut, timeUnit);
        setDatabase(DEFAULT);
    }
    public void set(K k,V v){
        set(DEFAULT, k,v, null);
    }
    public void set(K k,V v, Long microSeconds){
        set(DEFAULT, k,v, microSeconds);
    }
    public void set(K k,V v, Long microSeconds, final TimeUnit timeUnit){ set(DEFAULT, k,v, microSeconds, timeUnit); }
    public V get(int db,K k){
        setDatabase(db);
        V v = null;
        try {
            v = redisTemplate.opsForValue().get(k);
        }
        finally {
            setDatabase(DEFAULT);
        }
        return v;
    }
    public V getByPattern(int db, K k){
        long time1=System.currentTimeMillis();
        Set<K> keys;
        V v = null;
        try {
            setDatabase(db);
            keys = redisTemplate.keys(k);
        }
        finally {
            setDatabase(DEFAULT);
        }
        try{
            if(keys != null && !keys.isEmpty()){
                K firstKey = keys.iterator().next();
                setDatabase(db);
                v = redisTemplate.opsForValue().get(firstKey);
            }
        }
        finally {
            setDatabase(DEFAULT);
        }
        long time2=System.currentTimeMillis();
        System.out.println("当前程序耗时："+(time2-time1)+"ms");
        return v;
    }
    public Set<K> getKeys(int db, K k){
        setDatabase(db);
        Set<K> result = redisTemplate.keys(k);
        setDatabase(DEFAULT);
        return result;
    }
    public K getFirstKey(int db,K k){
        setDatabase(db);
        Set<K> keys = redisTemplate.keys(k);
        if(keys != null && !keys.isEmpty()) return keys.iterator().next();
        setDatabase(DEFAULT);
        return null;
    }
    public boolean tryGet(K k,V v){
        if(hasKey(k)){
            v = get(k);
            return v != null;
        }
        return false;
    }
    public V get(K k){
        return get(DEFAULT, k);
    }
    public Boolean hasKey(K k){
        setDatabase(DEFAULT);
        return redisTemplate.hasKey(k);
    }
    public RedisUtil(RedisConnectionFactory connectionFactory) {
        //this();
        this.setConnectionFactory(connectionFactory);
        this.afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
