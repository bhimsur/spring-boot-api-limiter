package io.bhimsur.apilimiter.service;

import io.bhimsur.apilimiter.constant.Plan;
import io.github.bucket4j.Bucket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PlanService.class)
public class PlanServiceTest {

    @Autowired
    private PlanService underTest;

    @Test
    public void resolve() {
        var response = underTest.resolve("FR-0x9c");
        Bucket expected = Bucket.builder()
                .addLimit(Plan.FREE.getLimit())
                .build();
        assertEquals(expected.getAvailableTokens(), response.getAvailableTokens());
    }
}