package com.mattjtodd.coherence;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by mattjtodd on 24/06/15.
 */
public class CachingBean {

    private Map<String, String> map = Collections.singletonMap("test", "TEST");

    @Cacheable("cachingBean")
    public String getValue(String key) {
        return getDirectValue(key);
    }

    @CachePut("cachingBean")
    public String putGetValue(String key) {
        return getDirectValue(key);
    }

    public String getDirectValue(String key) {
        Logger.getAnonymousLogger().info("** getting key");
        return map.get(key);
    }
}
