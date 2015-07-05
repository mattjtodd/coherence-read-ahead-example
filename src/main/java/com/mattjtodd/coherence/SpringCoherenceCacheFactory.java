package com.mattjtodd.coherence;

import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;

import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mattjtodd on 30/06/15.
 */
public class SpringCoherenceCacheFactory implements Function<String, CoherenceCache> {

    private final ConfigurableCacheFactory configurableCacheFactory;

    private final ClassLoader classLoader;

    public SpringCoherenceCacheFactory(ConfigurableCacheFactory configurableCacheFactory) {
        this(configurableCacheFactory, Thread.currentThread().getContextClassLoader());
    }

    public SpringCoherenceCacheFactory(ConfigurableCacheFactory configurableCacheFactory, ClassLoader classLoader) {
        this.configurableCacheFactory = checkNotNull(configurableCacheFactory);
        this.classLoader = checkNotNull(classLoader);
    }

    @Override
    public CoherenceCache apply(String cacheName) {
        NamedCache namedCache = configurableCacheFactory.ensureCache(cacheName, classLoader);
        return new CoherenceCache(namedCache);
    }
}
