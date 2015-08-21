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

import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.util.ResourceRegistry;

import java.util.Collection;

/**
 * Factory for creating configured {@name ConfigurableCacheFactory} instances.
 */
public final class ConfigurableCacheFactories {

  private ConfigurableCacheFactories() {
    // prevents construction
  }

  /**
   * Creates a factory which has resource.
   */
  public static ConfigurableCacheFactory withRegistrars(Collection<ResourceRegistrar>
                                                            resourceRegistrars,
                                                        ClassLoader classLoader) {
    ConfigurableCacheFactory configurableCacheFactory = withClassLoader(classLoader);
    ResourceRegistry resourceRegistry = configurableCacheFactory.getResourceRegistry();
    resourceRegistrars.forEach(
        resourceRegistrar -> resourceRegistrar.registerResource(resourceRegistry));
    return configurableCacheFactory;
  }

  public static ConfigurableCacheFactory withClassLoader(ClassLoader classLoader) {
    return CacheFactory.getConfigurableCacheFactory(classLoader);
  }
}

