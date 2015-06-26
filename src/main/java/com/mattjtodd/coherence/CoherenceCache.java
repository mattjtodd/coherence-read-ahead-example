package com.mattjtodd.coherence;

import com.tangosol.net.NamedCache;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * Created by mattjtodd on 24/06/15.
 */
public class CoherenceCache implements Cache {

    /**
     * Underlying Coherence {@code NamedCache}
     */
    private final NamedCache namedCache;

    /**
     * Constructor
     *
     * @param namedCache Coherence cache to use
     */
    public CoherenceCache(NamedCache namedCache) {
        this.namedCache = namedCache;
    }

    /**
     * Remove all mappings from the cache.
     */
    @Override
    public void clear() {
        namedCache.clear();
    }

    /**
     * Return the cache name.
     */
    @Override
    public String getName() {
        return namedCache.getCacheName();
    }

    /**
     * Return the the underlying native cache provider.
     */
    @Override
    public Object getNativeCache() {
        return namedCache;
    }

    /**
     * Return the value to which this cache maps the specified key. Returns
     * {@code null} if the cache contains no mapping for this key.
     *
     * @param key key whose associated value is to be returned.
     * @return the value to which this cache maps the specified key,
     *         or {@code null} if the cache contains no mapping for this key
     */
    @Override
    public ValueWrapper get(Object key) {
        Object value = namedCache.get(key);
        if ( value != null ) {
            return new SimpleValueWrapper(value);
        }
        return null;
    }

    /**
     * Associate the specified value with the specified key in this cache.
     * <p>If the cache previously contained a mapping for this key, the old
     * value is replaced by the specified value.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    @Override
    public void put(Object key, Object value) {
        namedCache.put(key, value);
    }

    /**
     * Atomically associate the specified value with the specified key in this cache
     * if it is not set already.
     * <p>This is equivalent to:
     * <pre><code>
     * Object existingValue = cache.get(key);
     * if (existingValue == null) {
     *     cache.put(key, value);
     *     return null;
     * } else {
     *     return existingValue;
     * }
     * </code></pre>
     * except that the action is performed atomically. While all out-of-the-box
     * {@link CacheManager} implementations are able to perform the put atomically,
     * the operation may also be implemented in two steps, e.g. with a check for
     * presence and a subsequent put, in a non-atomic way. Check the documentation
     * of the native cache implementation that you are using for more details.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the value to which this cache maps the specified key (which may be
     * {@code null} itself), or also {@code null} if the cache did not contain any
     * mapping for that key prior to this call. Returning {@code null} is therefore
     * an indicator that the given {@code value} has been associated with the key.
     * @since 4.1
     */
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        if (namedCache.containsKey(key)) {
            return new SimpleValueWrapper(namedCache.get(key));
        } else{
            namedCache.put(key, value);
            return null;
        }
    }

    /**
     * Evict the mapping for this key from this cache if it is present.
     *
     * @param key the key whose mapping is to be removed from the cache
     */
    @Override
    public void evict(Object key) {
        namedCache.remove(key);
    }

    /**
     * Return the value to which this cache maps the specified key,
     * generically specifying a type that return value will be cast to.
     * <p>Note: This variant of {@code get} does not allow for differentiating
     * between a cached {@code null} value and no cache entry found at all.
     * Use the standard {@link #get(Object)} variant for that purpose instead.
     *
     * @param key  the key whose associated value is to be returned
     * @param type the required type of the returned value
     * @return the value to which this cache maps the specified key
     * (which may be {@code null} itself), or also {@code null} if
     * the cache contains no mapping for this key
     * @see #get(Object)
     */
    @Override
    public <T> T get(Object key, Class<T> type) {
        Object value = namedCache.get(key);
        if ( value != null ) {
            return type.cast(value);
        }
        return null;
    }
}
