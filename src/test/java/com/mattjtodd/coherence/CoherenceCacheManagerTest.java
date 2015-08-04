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

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CoherenceCacheManagerTest {

    @InjectMocks
    private CoherenceCacheManager coherenceCacheManager;

    @Mock
    private Function<String, CoherenceCache> cacheFactory;

    @Mock
    private CoherenceCache coherenceCache;

    private String cacheName;

    @Before
    public void setUp() {
        cacheName = randomAlphabetic(6);
        when(cacheFactory.apply(cacheName)).thenReturn(coherenceCache);
    }

    @Test
    public void getCacheWhenEmptyCheckingNewCacheReturned() {
        assertEquals(coherenceCache, coherenceCacheManager.getCache(cacheName));
    }

    @Test
    public void getCacheWhenPopulatedCheckingSameCacheReturned() {
        coherenceCacheManager.getCache(cacheName);

        assertEquals(coherenceCache, coherenceCacheManager.getCache(cacheName));
        assertEquals(coherenceCache, coherenceCacheManager.getCache(cacheName));

        verify(cacheFactory, times(1)).apply(cacheName);
    }

    @Test
    public void getCacheNamesWhenEmptyCheckingEmpty() {
        assertThat(coherenceCacheManager.getCacheNames(), empty());
    }

    @Test
    public void getCacheNamesPopulateCheckingKeys() {
        coherenceCacheManager.getCache(cacheName);

        assertThat(coherenceCacheManager.getCacheNames(), Matchers.contains(cacheName));
    }
}
