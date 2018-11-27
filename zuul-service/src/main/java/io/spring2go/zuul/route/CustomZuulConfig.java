package io.spring2go.zuul.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CustomZuulConfig {

    @Autowired
    private ZuulProperties zuulProperties(){
        return new ZuulProperties();
    }

    @Autowired
    private ServerProperties serverProperties(){
        return new ServerProperties();
    }

    @Bean
    public CustomRouteLocator routeLocator(){
        CustomRouteLocator routeLocator = new CustomRouteLocator(serverProperties().getServlet().getPath(), zuulProperties());
        return routeLocator;
    }
}
