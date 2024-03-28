package ru.mirea.dictionary;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import ru.mirea.dictionary.config.RedisSchema;

import java.util.Arrays;
import java.util.List;

@Repository
public class DictionaryRedisRepository {
    private final HashOperations<String, String, String> redisHash;

    public DictionaryRedisRepository(StringRedisTemplate redis) {
        this.redisHash = redis.opsForHash();
    }

    public List<String> getData(String city, String condition, String month) {
        return redisHash.multiGet(
                String.format("%s.%s", city, condition),
                Arrays.asList(month, RedisSchema.DESCRIPTION));
    }
}
