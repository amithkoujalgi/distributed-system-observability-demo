package io.gitub.amithkoujalgi.demo.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gitub.amithkoujalgi.demo.models.http.Instrument;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Map;

public class StandaloneRedisAPIs {
    public static void main(String[] args) throws Exception {
        RedisTemplate<String, Object> redisTemplate = redisTemplateForStocks("localhost", 6379);
//        System.out.println(redisTemplate.opsForHash().entries("stocks:AAPL"));
//        System.out.println(redisTemplate.opsForHash().entries("indices:NASDAQ"));
//        System.out.println(redisTemplate.keys("stocks*"));

        Map<Object, Object> map = redisTemplate.opsForHash().entries("stocks:AAPL");
        for (Map.Entry<Object, Object> e : map.entrySet()) {
            System.out.println(e.getKey() + " - " + e.getValue());
        }
        Instrument x = Instrument.buildFromMap("AAPL", map);
        System.out.println(x.toJSON());

        List<Object> y = redisTemplate.opsForList().range("portfolio:user-1", 0, -1);
        System.out.println(new ObjectMapper().writeValueAsString(y));
    }


    public static RedisTemplate<String, Object> redisTemplateForStocks(String host, int port) {
        LettuceConnectionFactory cf = new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
        cf.afterPropertiesSet();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
         redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
