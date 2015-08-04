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

import com.tangosol.util.ResourceRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;

import static com.oracle.coherence.spring.SpringNamespaceHandler.DEFAULT_FACTORY_NAME;
import static org.mockito.Mockito.verify;

/**
 * Created by mattjtodd on 30/06/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class BeanFactoryResourceRegistrarTest {

    @InjectMocks
    private BeanFactoryResourceRegistrar registrar;

    @Mock
    private BeanFactory beanFactory;

    @Mock
    private ResourceRegistry resourceRegistry;

    @Test
    public void registerResourceCheckingRegistryCorrectlyInvoked() {
        registrar.registerResource(resourceRegistry);

        verify(resourceRegistry).registerResource(BeanFactory.class, DEFAULT_FACTORY_NAME, beanFactory);
    }
}
