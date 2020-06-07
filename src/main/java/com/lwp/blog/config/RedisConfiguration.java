package com.lwp.blog.config;

import com.lwp.blog.utils.FastJsonRedisSerializer;
import com.lwp.blog.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/06/06/13:59
 * @Description:
 */
@Configuration
public class RedisConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfiguration.class);

    //lettuce 客户端连接工厂
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    public void setSerializer(RedisTemplate<String,Object> template){
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        //key 采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //value序列化采用jackson
        template.setKeySerializer(fastJsonRedisSerializer);

        template.setHashKeySerializer(stringRedisSerializer);
        //hash的key 也采用String的序列化方式
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
    }
    @SuppressWarnings({"rawtypes","unchecked"})
    @Bean
    public RedisUtil defaultRedisTemplate(){
        RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(lettuceConnectionFactory);
        setSerializer(template);
        template.afterPropertiesSet();
        return new RedisUtil(template);
    }

}
