package cn.peyton.plum.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <h3>配置 Redis </h3>
 * <h4>extends CachingConfigurerSupport</h4>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2021/11/1 22:04
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@Slf4j
@Configuration
@EnableCaching  //启用缓存
public class RedisConfiguration {

    /**
     * 1. 创建 JedisPoolConfig 对象，在该对象中完成一些链接池配置。
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        //最大空闲数
       config.setMaxIdle(10);
        //最小空闲数
       config.setMinIdle(5);
        //最大链接数
       config.setMaxTotal(20);

        return config;
    }

    /**
     * 2.创建 JedisConnectionFactory,配置redis链接信息。
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig config) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        //关联链接池的配置对象
        factory.setPoolConfig(config);
        //配置链接 Redis 的信息
        factory.setHostName("127.0.0.1");
        //端口
        factory.setPort(6379);
        factory.setPassword("hc2919");

        return factory;
    }

    /**
     * 3. 创建RedisTemplate: 用于执行 Redis 操作方法
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        //关联
        template.setConnectionFactory(factory);

        //为 key 设置序列化器
        template.setKeySerializer(new StringRedisSerializer());
        //为 value 设置序列化器
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

}
