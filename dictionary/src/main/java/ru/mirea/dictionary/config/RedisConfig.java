package ru.mirea.dictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public StringRedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setEnableTransactionSupport(false);
        template.afterPropertiesSet();

        var data = new RedisData();

        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.MOSCOW, RedisSchema.TEMPERATURE), RedisSchema.mapMonthToValue(data.getMoscowTemperature(), RedisSchema.DESCRIPTION_TEMPERATURE));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.MOSCOW, RedisSchema.PRECIPITATION), RedisSchema.mapMonthToValue(data.getMoscowPrecipitation(), RedisSchema.DESCRIPTION_PRECIPITATION));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.MOSCOW, RedisSchema.SUNSHINE), RedisSchema.mapMonthToValue(data.getMoscowSunshine(), RedisSchema.DESCRIPTION_SUNSHINE));

        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.SAINT_PETERSBURG, RedisSchema.TEMPERATURE), RedisSchema.mapMonthToValue(data.getSaintPetersburgTemperature(), RedisSchema.DESCRIPTION_TEMPERATURE));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.SAINT_PETERSBURG, RedisSchema.PRECIPITATION), RedisSchema.mapMonthToValue(data.getSaintPetersburgPrecipitation(), RedisSchema.DESCRIPTION_PRECIPITATION));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.SAINT_PETERSBURG, RedisSchema.SUNSHINE), RedisSchema.mapMonthToValue(data.getSaintPetersburgSunshine(), RedisSchema.DESCRIPTION_SUNSHINE));

        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.KAZAN, RedisSchema.TEMPERATURE), RedisSchema.mapMonthToValue(data.getKazanTemperature(), RedisSchema.DESCRIPTION_TEMPERATURE));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.KAZAN, RedisSchema.PRECIPITATION), RedisSchema.mapMonthToValue(data.getKazanPrecipitation(), RedisSchema.DESCRIPTION_PRECIPITATION));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.KAZAN, RedisSchema.SUNSHINE), RedisSchema.mapMonthToValue(data.getKazanSunshine(), RedisSchema.DESCRIPTION_SUNSHINE));

        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.OMSK, RedisSchema.TEMPERATURE), RedisSchema.mapMonthToValue(data.getOmskTemperature(), RedisSchema.DESCRIPTION_TEMPERATURE));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.OMSK, RedisSchema.PRECIPITATION), RedisSchema.mapMonthToValue(data.getOmskPrecipitation(), RedisSchema.DESCRIPTION_PRECIPITATION));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.OMSK, RedisSchema.SUNSHINE), RedisSchema.mapMonthToValue(data.getOmskSunshine(), RedisSchema.DESCRIPTION_SUNSHINE));

        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.VLADIVOSTOK, RedisSchema.TEMPERATURE), RedisSchema.mapMonthToValue(data.getVladivostokTemperature(), RedisSchema.DESCRIPTION_TEMPERATURE));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.VLADIVOSTOK, RedisSchema.PRECIPITATION), RedisSchema.mapMonthToValue(data.getVladivostokPrecipitation(), RedisSchema.DESCRIPTION_PRECIPITATION));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.VLADIVOSTOK, RedisSchema.SUNSHINE), RedisSchema.mapMonthToValue(data.getVladivostokSunshine(), RedisSchema.DESCRIPTION_SUNSHINE));

        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.NOVOROSSIYSK, RedisSchema.TEMPERATURE), RedisSchema.mapMonthToValue(data.getNovorossiyskTemperature(), RedisSchema.DESCRIPTION_TEMPERATURE));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.NOVOROSSIYSK, RedisSchema.PRECIPITATION), RedisSchema.mapMonthToValue(data.getNovorossiyskPrecipitation(), RedisSchema.DESCRIPTION_PRECIPITATION));
        template.opsForHash().putAll(String.format("%s.%s", RedisSchema.NOVOROSSIYSK, RedisSchema.SUNSHINE), RedisSchema.mapMonthToValue(data.getNovorossiyskSunshine(), RedisSchema.DESCRIPTION_SUNSHINE));

        return template;
    }
}
