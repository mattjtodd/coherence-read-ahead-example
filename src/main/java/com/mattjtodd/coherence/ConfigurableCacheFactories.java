package com.mattjtodd.coherence;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.util.ResourceRegistry;

import java.util.Collection;

/**
 * Created by mattjtodd on 01/07/15.
 */
public final class ConfigurableCacheFactories {

    private ConfigurableCacheFactories() {}

    public static ConfigurableCacheFactory withRegistrars(Collection<ResourceRegistrar> resourceRegistrars, ClassLoader classLoader) {
        ConfigurableCacheFactory configurableCacheFactory = withClassLoader(classLoader);
        ResourceRegistry resourceRegistry = configurableCacheFactory.getResourceRegistry();
        resourceRegistrars.forEach(resourceRegistrar -> resourceRegistrar.registerResource(resourceRegistry));
        return configurableCacheFactory;
    }

    public static ConfigurableCacheFactory withClassLoader(ClassLoader classLoader) {
        return CacheFactory.getConfigurableCacheFactory(classLoader);
    }
}

