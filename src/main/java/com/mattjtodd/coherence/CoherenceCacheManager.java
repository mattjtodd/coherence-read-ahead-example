package com.mattjtodd.coherence;

import com.google.common.collect.ImmutableMap;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of Spring CacheManager for Coherence.
 */
public class CoherenceCacheManager implements CacheManager {

    private final AtomicReference<ImmutableMap<String, CoherenceCache>> caches;

    private final Function<String, CoherenceCache> cacheFactory;

    public CoherenceCacheManager(Function<String, CoherenceCache> cacheFactory) {
        this.cacheFactory = checkNotNull(cacheFactory);
        caches = new AtomicReference<>(ImmutableMap.of());
    }

    @Override
    public CoherenceCache getCache(String name) {
        return caches.updateAndGet(caches -> createCache(caches, name)).get(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return caches.get().keySet();
    }

    private ImmutableMap<String, CoherenceCache> createCache(ImmutableMap<String, CoherenceCache> caches, String name) {
        ImmutableMap<String, CoherenceCache> map = caches;
        if (!caches.containsKey(name)) {
            map = ImmutableMap.<String, CoherenceCache>builder().putAll(caches).put(name, cacheFactory.apply(name)).build();
        }
        return map;
    }
}
