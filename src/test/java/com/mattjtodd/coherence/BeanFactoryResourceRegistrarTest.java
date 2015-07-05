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
