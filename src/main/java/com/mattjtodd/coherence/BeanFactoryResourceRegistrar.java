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
import static com.oracle.coherence.spring.SpringNamespaceHandler.DEFAULT_FACTORY_NAME;

import com.tangosol.util.ResourceRegistry;

import org.springframework.beans.factory.BeanFactory;

/**
 * Registers a {@name BeanFactory} with a {@name ResourceRegistry}.
 */
public class BeanFactoryResourceRegistrar implements ResourceRegistrar {
  private final BeanFactory beanFactory;

  /**
   * Creates a new instance with the supplied factory.
   *
   * @param beanFactory the bean factory to be registered
   */
  public BeanFactoryResourceRegistrar(BeanFactory beanFactory) {
    this.beanFactory = checkNotNull(beanFactory);
  }

  @Override
  public String registerResource(ResourceRegistry resourceRegistry) {
    return resourceRegistry.registerResource(BeanFactory.class, DEFAULT_FACTORY_NAME, beanFactory);
  }
}
