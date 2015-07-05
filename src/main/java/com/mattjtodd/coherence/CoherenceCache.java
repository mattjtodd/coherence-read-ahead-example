package com.mattjtodd.coherence;

import com.tangosol.net.NamedCache;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Coherence {@code Cache} implementation.
 */
public class CoherenceCache implements Cache {

    private final NamedCache namedCache;

    /**
     * Backed by the supplied {@code NamedCache}.
     *
     * @param namedCache Coherence cache to use
     */
    public CoherenceCache(NamedCache namedCache) {
        this.namedCache = checkNotNull(namedCache);
    }

    @Override
    public void clear() {
        namedCache.clear();
    }

    @Override
    public String getName() {
        return namedCache.getCacheName();
    }

    @Override
    public Object getNativeCache() {
        return namedCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        return Optional.ofNullable(namedCache.get(key))
            .map(SimpleValueWrapper::new)
            .orElse(null);
    }

    @Override
    public void put(Object key, Object value) {
        namedCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return Optional.ofNullable(namedCache.putIfAbsent(key, value))
            .map(SimpleValueWrapper::new)
            .orElse(null);
    }

    @Override
    public void evict(Object key) {
        namedCache.remove(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return Optional.ofNullable(namedCache.get(key))
            .map(type::cast)
            .orElse(null);
    }
}
