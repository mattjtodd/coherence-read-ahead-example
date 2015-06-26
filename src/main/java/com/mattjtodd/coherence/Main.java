package com.mattjtodd.coherence;

import com.tangosol.net.CacheFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.Collections.nCopies;

/**
 * Created by mattjtodd on 16/06/15.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");

        CachingBean cachingBean = applicationContext.getBean(CachingBean.class);

        Runnable runnable = () -> nCopies(120, "k1")
            .forEach(key -> sleepQuietly(System.currentTimeMillis() + " " + cachingBean.getValue("test")));
        Future<?> submit = Executors.newCachedThreadPool().submit(runnable);
        submit.get();

        CacheFactory.shutdown();
    }

    private static void sleepQuietly(String message) {
        System.out.println(message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
