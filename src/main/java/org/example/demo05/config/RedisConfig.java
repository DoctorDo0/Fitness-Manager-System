package org.example.demo05.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfig implements WebMvcConfigurer {

    //手动创建一个拦截器
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mpi = new MybatisPlusInterceptor();
        mpi.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mpi;
    }

    /**
     * 自定义json序列化器
     *
     * @return 自定义json序列化器
     */
    @Bean
    public JacksonJsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

        JsonMapper.Builder builder = JsonMapper.builder();

        builder.changeDefaultVisibility(vc -> vc
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withCreatorVisibility(JsonAutoDetect.Visibility.ANY)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.ANY)
        );

        builder.activateDefaultTyping(ptv, DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        builder.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //使用JacksonJsonRedisSerializer来序列化和反序列化redis的value值（默认使用jdk的序列化方式：JdkSerializationRedisSerializer）
        return new JacksonJsonRedisSerializer<>(builder.build(), Object.class);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       JacksonJsonRedisSerializer<Object> valueSerializer) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(valueSerializer);
        redisTemplate.setValueSerializer(valueSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * 自定义RedisCacheManager是为了自定义序列化器。
     * 默认的CacheManager使用默认的JDK序列化器，序列化结果为字节数组，不易读。且需要模型类必须实现Serializable接口（目标类的引用类型的属性也需要实现此接口）
     *
     * @return 自定义RedisCacheManager，可以序列化对象
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory,
                                          JacksonJsonRedisSerializer<Object> valueSerializer) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
                .entryTtl(Duration.ofHours(4));// 设置缓存全局统一有效期，ttl表示time to live，即存活时间。
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }
}
