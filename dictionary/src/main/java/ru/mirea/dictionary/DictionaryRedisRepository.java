package ru.mirea.dictionary;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class DictionaryRedisRepository {
    private final HashOperations<String, String, String> redisHash;

    public DictionaryRedisRepository(StringRedisTemplate redis) {
        this.redisHash = redis.opsForHash();
    }

    public List<String> getData(String city, String type, String month) {
        return redisHash.multiGet(
                String.format("%s.%s", city, type),
                Arrays.asList(month, "Description"));
    }
}
