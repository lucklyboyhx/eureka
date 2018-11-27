package io.spring2go.zuul.route.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.spring2go.zuul.route.service.RefreshRouteService;


@RestController
public class RefreshZuulRouteController {

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
}
