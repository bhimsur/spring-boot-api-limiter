package io.bhimsur.apilimiter.service;

import io.bhimsur.apilimiter.constant.Plan;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlanService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolve(String key) {
        return cache.computeIfAbsent(key, this::init);
    }

    private Bucket init(String key) {
        Plan plan = Plan.resolve(key);
        return Bucket.builder()
                .addLimit(plan.getLimit())
                .build();
    }
}
