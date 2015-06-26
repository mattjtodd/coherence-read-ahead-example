/*
 *  Copyright 2008 - 2015: Thomson Reuters Global Resources. All Rights Reserved. Proprietary and Confidential
 *    information of TRGR Disclosure, Use or Reproduction without the written authorization of TRGR is prohibited.
 */
package com.mattjtodd.coherence;

import com.oracle.coherence.spring.SpringNamespaceHandler;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;

/**
 * Binds the spring ApplicationContext into the Coherence resource registry.
 */
public class CoherenceCacheBeanFactoryBinder implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Register the application context with the ConfigurableCacheFactory.
     */
    public void init() {
        ConfigurableCacheFactory factory = CacheFactory.getCacheFactoryBuilder()
                                                       .getConfigurableCacheFactory(ClassUtils.getDefaultClassLoader());

        factory.getResourceRegistry().registerResource(
            BeanFactory.class, SpringNamespaceHandler.DEFAULT_FACTORY_NAME, applicationContext);
    }
}
