package com.mattjtodd.coherence;

import com.tangosol.net.NamedCache;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by mattjtodd on 30/06/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CoherenceCacheTest {

    @InjectMocks
    private CoherenceCache coherenceCache;

    @Mock
    private NamedCache namedCache;

    private String key;

    private String value;

    @Before
    public void setUp() {
        key = randomAlphabetic(6);
        value = randomAlphabetic(7);
    }

    @Test
    public void clearCheckingDelegateCalled() {
        coherenceCache.clear();

        verify(namedCache).clear();
    }

    @Test
    public void getNameCheckingDelegateCalled() {
        String cacheName = randomAlphabetic(6);
        when(namedCache.getCacheName()).thenReturn(cacheName);

        assertThat(coherenceCache.getName(), is(cacheName));
    }

    @Test
    public void getNativeCacheCheckingDelegateReturned() {
        assertSame(coherenceCache.getNativeCache(), namedCache);
    }

    @Test
    public void getWhenKeyPresentCheckingWrappedContainsCorrectValue() {
        when(namedCache.get(key)).thenReturn(value);

        assertThat(coherenceCache.get(key).get(), is(value));
    }

    @Test
    public void getWhenKeyNotPresentCheckingNull() {
        when(namedCache.get(key)).thenReturn(null);

        assertNull(coherenceCache.get(key));
    }

    @Test
    public void putCheckingDelegateCalled() {
        coherenceCache.put(key, value);

        verify(namedCache).put(key, value);
    }

    @Test
    public void putIfAbsentCheckingWrappedContainsDelegateResult() {
        String oldValue = randomAlphabetic(8);

        when(namedCache.putIfAbsent(key, value)).thenReturn(oldValue);

        assertThat(coherenceCache.putIfAbsent(key, value).get(), is(oldValue));
    }

    @Test
    public void putIfAbsentCheckingWrappedContainsNullWhenDelegateNull() {
        when(namedCache.putIfAbsent(key, value)).thenReturn(null);

        assertNull(coherenceCache.putIfAbsent(key, value));
    }

    @Test
    public void evict() {
        coherenceCache.evict(key);

        verify(namedCache).remove(key);
    }

    @Test
    public void getWhenCacheValueIsStringCheckingValueCorrect() {
        when(namedCache.get(key)).thenReturn(value);

        assertThat(coherenceCache.get(key).get(), Matchers.is(value));
    }

    @Test
    public void getWhenCacheValueIsNullCheckingKeyNotMapped() {
        when(namedCache.get(key)).thenReturn(null);

        assertNull(coherenceCache.get(key));
    }
}
