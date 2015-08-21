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

import com.tangosol.net.NamedCache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Optional;

/**
 * Coherence {@code Cache} implementation.
 */
public class CoherenceCache implements Cache {

  private final NamedCache namedCache;

  /**
   * Backed by the supplied {@code NamedCache}.
   *
   * @param namedCache Coherence cache which is delegated to
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
  public <T> T get(Object key, Class<T> type) {
    return Optional.ofNullable(namedCache.get(key))
        .map(type::cast)
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

}
