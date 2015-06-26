package com.mattjtodd.coherence;

import org.springframework.beans.factory.annotation.Required;

import java.util.logging.Logger;

/**
 * Created by mattjtodd on 24/06/15.
 */
public class CacheWarmer {
    private CachingBean cachingBean;

    public void warm() {
        Logger.getAnonymousLogger().info("Warming...........");
        cachingBean.putGetValue("test");
        Logger.getAnonymousLogger().info("Done...........");
    }

    @Required
    public void setCachingBean(CachingBean cachingBean) {
        this.cachingBean = cachingBean;
    }
}
