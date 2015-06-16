package com.mattjtodd.coherence;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * Created by mattjtodd on 16/06/15.
 */
public class CacheExample {
    public static void main(String[] args) throws Exception {
        String key = "k1";
        CacheFactory.ensureCluster();
        NamedCache cache = CacheFactory.getCache("dollar-bill");

        Future<?> submit = Executors.newCachedThreadPool().submit(() -> IntStream.range(0, 1000).forEach(x -> {
            System.out.println(System.currentTimeMillis() + " " + cache.get(key));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        submit.get();
        CacheFactory.shutdown();
    }
}
