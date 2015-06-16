package com.mattjtodd.coherence;

import com.tangosol.net.cache.CacheLoader;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by mattjtodd on 16/06/15.
 */
public class TestClassLoader implements CacheLoader {

    @Override
    public Object load(Object o) {
        System.out.println("Load o: " + o);
        try {
            Thread.sleep(1000 * 14);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    @Override
    public Map loadAll(Collection collection) {
        System.out.println("Loading all: " + collection);
        return Collections.singletonMap("key", System.currentTimeMillis());
    }
}
