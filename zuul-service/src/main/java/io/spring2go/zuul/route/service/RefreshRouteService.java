package io.spring2go.zuul.route.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;



@Service
public class RefreshRouteService {
//    private static final Logger log = LoggerFactory.getLogger(RefreshRouteService.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private ZuulRouteVoService zuulRouteVoService;

    public void refreshRoute(){
        zuulRouteVoService.initZuulRouteVoMap();

        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }
}
