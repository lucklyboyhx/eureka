package io.spring2go.zuul.route.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;

@Service
public class RateLimitService {
    private static final Logger log = LoggerFactory.getLogger(RateLimitService.class);
    
    public static Map<String, RateLimiter> MAP = Maps.newConcurrentMap();
    
    public void setRateLimitMap() {
        MAP.put("HELLO-CLIENT", RateLimiter.create(10.0));
        
        log.info(MAP.toString());
    }

}
