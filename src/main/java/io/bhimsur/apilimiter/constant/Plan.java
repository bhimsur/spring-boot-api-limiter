package io.bhimsur.apilimiter.constant;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;

public enum Plan {
    FREE {
        public Bandwidth getLimit() {
            return Bandwidth.classic(20, Refill.intervally(20, Duration.ofHours(1)));
        }
    },
    BASIC {
        public Bandwidth getLimit() {
            return Bandwidth.classic(50, Refill.intervally(40, Duration.ofHours(1)));
        }
    },
    COMPLETE {
        public Bandwidth getLimit() {
            return Bandwidth.classic(150, Refill.intervally(150, Duration.ofHours(1)));
        }
    };

    public static Plan resolve(String key) {
        if (null == key || key.isEmpty()) {
            return FREE;
        } else if (key.startsWith("BSX-")) {
            return BASIC;
        } else if (key.startsWith("CPL-")) {
            return COMPLETE;
        } else {
            return FREE;
        }
    }

    public abstract Bandwidth getLimit();
}
