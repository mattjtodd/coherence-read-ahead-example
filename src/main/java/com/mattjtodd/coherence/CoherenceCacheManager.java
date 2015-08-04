/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mattjtodd.coherence;

import com.google.common.collect.ImmutableMap;
import com.tangosol.net.CacheFactory;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of Spring {@name CacheManager} for named {@name CoherenceCache} instances.
 *
 * <p>This implementation uses {@name AtomicReference} and {@name ImmutableMap} for it's implementation</p>
 */
public class CoherenceCacheManager implements CacheManager {

    private final AtomicReference<ImmutableMap<String, CoherenceCache>> caches;

    private final Function<String, CoherenceCache> cacheFactory;

    /**
     * Creates a default function for cache creation.
     */
    public CoherenceCacheManager() {
        this(name -> new CoherenceCache(CacheFactory.getCache(name)));
    }

    /**
     * Uses the supplied function for cached creation.
     *
     * @param cacheFactory the function to use for creating caches
     */
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

    /**
     * Create a new cache if required and return the new caches map state.
     *
     * @param caches the current state of the caches map
     * @param name the name of the cache to create
     * @return the new state of the caches map
     */
    private ImmutableMap<String, CoherenceCache> createCache(ImmutableMap<String, CoherenceCache> caches, String name) {
        ImmutableMap<String, CoherenceCache> map = caches;
        if (!caches.containsKey(name)) {
            map = ImmutableMap.<String, CoherenceCache>builder()
                              .putAll(caches).put(name, cacheFactory.apply(name)).build();
        }
        return map;
    }
}
