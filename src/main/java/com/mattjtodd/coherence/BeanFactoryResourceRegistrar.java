package com.mattjtodd.coherence;

import com.google.common.base.Preconditions;
import com.tangosol.util.ResourceRegistry;
import org.springframework.beans.factory.BeanFactory;

import static com.oracle.coherence.spring.SpringNamespaceHandler.DEFAULT_FACTORY_NAME;

/**
 * Created by mattjtodd on 30/06/15.
 */
public class BeanFactoryResourceRegistrar implements ResourceRegistrar {

    private final BeanFactory beanFactory;

    public BeanFactoryResourceRegistrar(BeanFactory beanFactory) {
        this.beanFactory = Preconditions.checkNotNull(beanFactory);
    }

    @Override
    public String registerResource(ResourceRegistry resourceRegistry) {
        return resourceRegistry.registerResource(BeanFactory.class, DEFAULT_FACTORY_NAME, beanFactory);
    }
}
