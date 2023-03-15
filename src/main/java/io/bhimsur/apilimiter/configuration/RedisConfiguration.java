package io.bhimsur.apilimiter.configuration;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;

@Configuration
public class RedisConfiguration {

    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private String port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.db.1}")
    private int database1;

    @Bean
    public Config config() {
        String url = "redis://" + this.host + ':' + this.port;
        Config config = new Config();
        config.useSingleServer().setAddress(url).setPassword(this.password).setDatabase(this.database1);
        return config;
    }

    @Bean
    public CacheManager cacheManager(Config config) {
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        cacheManager.createCache("limiter", RedissonConfiguration.fromConfig(config));
        return cacheManager;
    }

    @Bean
    public ProxyManager<String> proxyManager(CacheManager cacheManager) {
        return new JCacheProxyManager<>(cacheManager.getCache("limiter"));
    }

}
