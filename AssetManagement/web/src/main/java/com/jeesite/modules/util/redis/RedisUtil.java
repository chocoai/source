package com.jeesite.modules.util.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RedisUtil<K,V> extends RedisTemplate<K,V> {
    public static final int DEFAULT =0;     //Default OR User
    public static final int DEPT = 1;
    @Autowired
    private RedisTemplate<K,V> redisTemplate;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    public JedisConnectionFactory getJedisConnectionFactory() {
        if(jedisConnectionFactory == null) {
            jedisConnectionFactory =(JedisConnectionFactory)redisTemplate.getConnectionFactory();
        }
        return jedisConnectionFactory;
    }

    public void setDatabase(int db){
        //RedisConnectionFactory c = redisTemplate.getConnectionFactory();
        //JedisConnectionFactory jedisConnectionFactory =  (JedisConnectionFactory)redisTemplate.getConnectionFactory();
        getJedisConnectionFactory().setDatabase(db);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
    }

    public void set(int db,K k,V v){
        setDatabase(db);
        redisTemplate.opsForValue().set(k,v);
        setDatabase(DEFAULT);
    }
    public void set(K k,V v){
        set(DEFAULT, k,v);
    }
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
    public boolean tryGet(K k,V v){
        if(redisTemplate.hasKey(k)){
            v = redisTemplate.opsForValue().get(k);
            return true;
        }
        return false;
    }
    public V get(K k){
        return get(DEFAULT, k);
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
