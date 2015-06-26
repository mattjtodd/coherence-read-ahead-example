package com.mattjtodd.coherence;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by mattjtodd on 24/06/15.
 */
public class CoherenceCacheManager  implements CacheManager {

    /**
     * Map of existing caches
     */
    private final ConcurrentMap<String, CoherenceCache> caches = new ConcurrentHashMap<>();

    /**
     * Default constructor - Warms up the Coherence cluster
     */
    public CoherenceCacheManager() {
        CacheFactory.ensureCluster();
    }

    /**
     * Return the cache associated with the given name.
     *
     * @param name cache identifier (must not be {@code null})
     *
     * @return associated cache, or {@code null} if none is found
     */
    @Override
    public Cache getCache(String name) {
        CoherenceCache cache = caches.get(name);
        if (cache == null) {
            final NamedCache namedCache = CacheFactory.getCache(name);
            cache = new CoherenceCache(namedCache);
            final CoherenceCache currentCache = caches.putIfAbsent(name, cache);
            if (currentCache != null) {
                cache = currentCache;
            }
        }
        return cache;
    }

    /**
     * Return a collection of the caches known by this cache manager.
     *
     * @return names of caches known by the cache manager.
     */
    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(caches.keySet());
    }
}
