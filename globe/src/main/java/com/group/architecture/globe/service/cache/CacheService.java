package com.group.architecture.globe.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.architecture.globe.model.common.CacheStoredEntity;
import com.group.architecture.globe.model.response.ContinentResponse;
import com.group.architecture.globe.utils.MD5Utils;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private final static String HASH_KEY = "Continent";

    private final static String HASH_ID = "hashValues";

    public String saveContinentInCache(ContinentResponse response) throws Exception {

        String json = mapper.writeValueAsString(response);
        String etag = MD5Utils.digest(json);

        redisTemplate.opsForHash().put(HASH_ID, response.getId(), new CacheStoredEntity(etag));
        redisTemplate.opsForHash().put(HASH_KEY, etag, json);

        return etag;
    }

    public ContinentResponse getContinentByEtag(long continentId, String etag) throws NotFoundException, JsonProcessingException {
        CacheStoredEntity entity = (CacheStoredEntity) redisTemplate.opsForHash().get(HASH_ID, continentId);
        if (entity == null || !etag.equals(entity.getEtag())) {
            throw new NotFoundException(String.format("Couldn't find continent with id %s in cache", continentId));
        }

        String  json = (String) redisTemplate.opsForHash().get(HASH_KEY, etag);
        return mapper.readValue(json,ContinentResponse.class);
    }

    public void deleteTag(long continentId) {
        CacheStoredEntity entity = (CacheStoredEntity) redisTemplate.opsForHash().get(HASH_ID, continentId);
        if (entity == null) {
            log.error(String.format("Couldn't find continent with id %s in cache", continentId));
            return;
        }
        String etag = entity.getEtag();
        redisTemplate.opsForHash().delete(HASH_ID,continentId);
        redisTemplate.opsForHash().delete(HASH_KEY,etag);
    }
}
