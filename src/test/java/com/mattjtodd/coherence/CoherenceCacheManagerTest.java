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

/**
 * Created by mattjtodd on 30/06/15.
 */
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
