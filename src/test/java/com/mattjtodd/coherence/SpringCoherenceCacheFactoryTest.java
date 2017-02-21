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

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by mattjtodd on 01/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpringCoherenceCacheFactoryTest {

  @InjectMocks
  private SpringCoherenceCacheFactory springCoherenceCacheFactory;

  @Mock
  private ConfigurableCacheFactory configurableCacheFactory;

  @Mock
  private NamedCache namedCache;

  @Mock
  private ClassLoader classLoader;

  @Test
  public void applyCheckingCacheFactoryAppliedCorrectly() {
    String cacheName = randomAlphabetic(6);
    when(configurableCacheFactory.ensureCache(cacheName, classLoader)).thenReturn(namedCache);

    assertEquals(namedCache, springCoherenceCacheFactory.apply(cacheName).getNativeCache());
  }
}
