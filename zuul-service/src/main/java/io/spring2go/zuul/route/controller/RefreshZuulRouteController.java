package io.spring2go.zuul.route.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.filters.FilterRegistry;

import io.spring2go.zuul.route.service.RefreshRouteService;


@RestController
public class RefreshZuulRouteController {
    
    private static final Logger log = LoggerFactory.getLogger(RefreshZuulRouteController.class);

    @Autowired
    RefreshRouteService refreshRouteService;

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public String refreshRoute(){
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping(value = "/watchNowRoute", method = RequestMethod.GET)
    public String watchNowRoute(){
        Map<String, Object> handleMap = zuulHandlerMapping.getHandlerMap();
        List<String> handleList = new ArrayList<String>();
        handleMap.forEach((key, value) ->{
            handleList.add(key);
        });
        return JSON.toJSONString(handleList);
    }
    
    @RequestMapping(value = "/removefilter/{name}", method = RequestMethod.GET)
    public String getFilter(@PathVariable("name") String name){
        Collection<ZuulFilter> filters = FilterRegistry.instance().getAllFilters();
//        FilterRegistry filterRegistry = FilterRegistry.instance();
        for (Iterator iterator = filters.iterator(); iterator.hasNext();) {
            ZuulFilter zuulFilter = (ZuulFilter) iterator.next();
            if (zuulFilter.getClass().getSimpleName().equals(name)) {
                log.info("删除过滤器：" + name);
                filters.remove(zuulFilter);
            }
        }
        return "success";
    }
}
