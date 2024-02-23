package com.github.pavelvil.jwtexample.configuration;

import com.github.pavelvil.jwtexample.entity.RefreshToken;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    public ReactiveRedisTemplate<String, RefreshToken> refreshTokenRedisTemplate(
            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<RefreshToken> valueSerializer = new Jackson2JsonRedisSerializer<>(RefreshToken.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, RefreshToken> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, RefreshToken> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }

}
