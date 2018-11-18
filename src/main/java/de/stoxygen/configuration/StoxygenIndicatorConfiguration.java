package de.stoxygen.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class StoxygenIndicatorConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(StoxygenIndicatorConfiguration.class);

    @Value("${downloader.url:}")
    private String downloader_url;

    @Value("${jedis.host}")
    private String jedis_host;

    @Value("${jedis.port}")
    private Integer jedis_port;


    public String getDownloader_url() {
        return downloader_url;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(jedis_host);
        jedisConnectionFactory.setPort(jedis_port);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
