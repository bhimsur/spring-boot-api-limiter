package io.bhimsur.apilimiter.filter;

import io.bhimsur.apilimiter.service.PlanService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LimiterInterceptor implements HandlerInterceptor {
    @Autowired
    private PlanService planService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = request.getHeader("X-Key");
        if (null == key || key.isEmpty()) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Missing Header: X-Key");
            return false;
        }

        Bucket bucket = planService.resolve(key);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            long waitTime = probe.getNanosToWaitForRefill() / 1_000_000_000L;
            response.addHeader("X-Retry-After-Seconds", String.valueOf(waitTime));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Limit Quota Reached");
            return false;
        }
    }

}
