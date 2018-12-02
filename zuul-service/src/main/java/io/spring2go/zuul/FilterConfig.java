package io.spring2go.zuul;

import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

import com.netflix.zuul.ZuulFilter;

import io.spring2go.zuul.route.filter.RateLimitZuulFilter;
import io.spring2go.zuul.route.filter.RouteTimesFilter;
import io.spring2go.zuul.route.filter.ZuulAutoFilter;

@Configuration
public class FilterConfig {
    
    @Bean
    public ZuulAutoFilter zuulAutoFilter(){
        return new ZuulAutoFilter();
    }
    
    @Bean
    public ZuulFilter routeTimesFilter(RouteLocator routeLocator){
        return new RouteTimesFilter(routeLocator,new UrlPathHelper());
    }

    @Bean
    public RateLimitZuulFilter rateLimitZuulFilter(){
        return new RateLimitZuulFilter();
    }
}
