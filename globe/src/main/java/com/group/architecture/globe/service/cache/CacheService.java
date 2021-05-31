package com.group.architecture.globe.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.architecture.globe.model.response.ContinentResponse;
import com.group.architecture.globe.repository.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CacheService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CacheRepository cacheRepository;

    public Map saveContinentInCache(ContinentResponse response) throws JsonProcessingException {
        Map responseMap = new HashMap();

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

        responseMap.put(hashtext, response);

        return responseMap;
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
