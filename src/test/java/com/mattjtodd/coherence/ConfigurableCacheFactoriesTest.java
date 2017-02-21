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

import static com.mattjtodd.coherence.ConfigurableCacheFactories.withClassLoader;
import static com.mattjtodd.coherence.ConfigurableCacheFactories.withRegistrars;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.util.ResourceRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Tests for the ConfigurableCacheFactories.
 */
@PrepareForTest(CacheFactory.class)
public class ConfigurableCacheFactoriesTest {

  @Rule
  public PowerMockRule rule = new PowerMockRule();

  @Mock
  private ClassLoader classLoader;

  @Mock
  private ConfigurableCacheFactory configurableCacheFactory;

  /**
   * Setup.
   */
  @Before
  public void setUp() {
    mockStatic(CacheFactory.class);

    when(CacheFactory.getConfigurableCacheFactory(classLoader))
        .thenReturn(configurableCacheFactory);
  }

  @Test(expected = AssertionError.class)
  public void constructReflectivelyExpectingAssertionErrorThrown() throws Exception {
    Whitebox.invokeConstructor(ConfigurableCacheFactories.class);
  }

  @Test
  public void withClassLoaderCheckingClassLoaderConsumedCorrectly() {
    assertEquals(configurableCacheFactory, withClassLoader(classLoader));
  }

  @Test
  public void withRegistrarsCheckingClassLoaderConsumedCorrectly() {
    assertEquals(configurableCacheFactory, withRegistrars(Collections.EMPTY_LIST, classLoader));
  }

  @Test
  public void withRegistrarsCheckingRegistrarsInvokedCorrectly() {
    ResourceRegistry resourceRegistry = Mockito.mock(ResourceRegistry.class);
    ResourceRegistrar resourceRegistrar = Mockito.mock(ResourceRegistrar.class);

    when(configurableCacheFactory.getResourceRegistry()).thenReturn(resourceRegistry);
    assertEquals(configurableCacheFactory,
        withRegistrars(Collections.singleton(resourceRegistrar), classLoader));

    verify(resourceRegistrar).registerResource(resourceRegistry);
  }
}
