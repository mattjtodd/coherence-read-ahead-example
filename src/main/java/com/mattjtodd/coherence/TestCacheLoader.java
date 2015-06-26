package com.mattjtodd.coherence;

import com.tangosol.net.cache.CacheLoader;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by mattjtodd on 16/06/15.
 */
public class TestCacheLoader implements CacheLoader {

    private CachingBean cachingBean;

    @Override
    public Object load(Object o) {

        try {
            System.out.println("Loading value: " + o);
            Thread.sleep(1000 * 14);
            return cachingBean.getDirectValue(o.toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map loadAll(Collection collection) {
        System.out.println("Loading all: " + collection);
        return Collections.singletonMap("key", System.currentTimeMillis());
    }

    @Required
    public void setCachingBean(CachingBean cachingBean) {
        this.cachingBean = cachingBean;
    }
}
