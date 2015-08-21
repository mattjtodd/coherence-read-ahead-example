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

import static com.google.common.base.Preconditions.checkNotNull;

import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;

import java.util.function.Function;

/**
 * Created by mattjtodd on 30/06/15.
 */
public class SpringCoherenceCacheFactory implements Function<String, CoherenceCache> {

  private final ConfigurableCacheFactory configurableCacheFactory;

  private final ClassLoader classLoader;

  public SpringCoherenceCacheFactory(ConfigurableCacheFactory configurableCacheFactory) {
    this(configurableCacheFactory, Thread.currentThread().getContextClassLoader());
  }

  public SpringCoherenceCacheFactory(ConfigurableCacheFactory configurableCacheFactory,
                                     ClassLoader classLoader) {
    this.configurableCacheFactory = checkNotNull(configurableCacheFactory);
    this.classLoader = checkNotNull(classLoader);
  }

  @Override
  public CoherenceCache apply(String cacheName) {
    NamedCache namedCache = configurableCacheFactory.ensureCache(cacheName, classLoader);
    return new CoherenceCache(namedCache);
  }
}
