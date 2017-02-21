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
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tangosol.net.NamedCache;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

/**
 * Created by mattjtodd on 30/06/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CoherenceCacheTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

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

  @Test
  public void getWhenCacheValueIsStringExpectingDateExpectingClassCastException() {
    when(namedCache.get(key)).thenReturn(randomAlphabetic(6));

    expectedException.expect(ClassCastException.class);
    expectedException.expectMessage("Cannot cast java.lang.String to java.util.Date");

    coherenceCache.get(key, Date.class);
  }

  @Test
  public void getWhenCacheValueIsNullExpectingNull() {
    when(namedCache.get(key)).thenReturn(null);

    assertNull(coherenceCache.get(key, Date.class));
  }
}
