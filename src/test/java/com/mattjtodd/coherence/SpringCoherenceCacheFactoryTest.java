package com.mattjtodd.coherence;

import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
