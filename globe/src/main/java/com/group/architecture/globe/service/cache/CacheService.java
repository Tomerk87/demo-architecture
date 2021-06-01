package com.group.architecture.globe.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.architecture.globe.model.common.CacheStoredEntity;
import com.group.architecture.globe.model.response.ContinentResponse;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class CacheService {

    @Autowired
    private ObjectMapper mapper;

    //TODO:CHECK IF NEED TO BE DELETED
//    @Autowired
//    private CacheRepository cacheRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private final static String HASH_KEY = "Continent";

    private final static String HASH_ID = "hashValues";

    public String saveContinentInCache(ContinentResponse response) throws JsonProcessingException {

        String etag = checksum(response);

        redisTemplate.opsForHash().put(HASH_ID, response.getId(), new CacheStoredEntity(etag));
        redisTemplate.opsForHash().put(HASH_KEY, etag, response);

        return etag;
    }

    public ContinentResponse getContinentByEtag(String continentId, String etag) throws NotFoundException {
        Boolean containsId = redisTemplate.opsForHash().hasKey(HASH_ID, continentId);
        if (!containsId) {
            throw new NotFoundException(String.format("Continent id %s was not found in cache", continentId));
        }

        CacheStoredEntity entity = (CacheStoredEntity) redisTemplate.opsForHash().get(HASH_ID, continentId);
        if (entity == null || !etag.equals(entity.getEtag())) {
            throw new NotFoundException(String.format("Couldn't find continent with id %s in cache", continentId));
        }

        ContinentResponse response = (ContinentResponse) redisTemplate.opsForHash().get(HASH_KEY, etag);
        return response;
    }

    public void deleteTag(String continentId) {
        CacheStoredEntity entity = (CacheStoredEntity) redisTemplate.opsForHash().get(HASH_ID, continentId);
        if (entity == null) {
            log.error(String.format("Couldn't find continent with id %s in cache", continentId));
            return;
        }
        String etag = entity.getEtag();
        redisTemplate.opsForHash().delete(HASH_ID,continentId);
        redisTemplate.opsForHash().delete(HASH_KEY,etag);
    }


    private String checksum(ContinentResponse response) throws JsonProcessingException {
        String responseAsString = mapper.writeValueAsString(response);
        MessageDigest md5 = getDigest();
        md5.update(responseAsString.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md5.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);

        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }

        md5.reset();
        return hashtext;
    }

    private MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error(String.format("Failed to get algorithm for md5. Error: %s", e.getMessage()),e);
        }
        return null;
    }
}
