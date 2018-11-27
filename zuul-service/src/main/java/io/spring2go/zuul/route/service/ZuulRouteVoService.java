package io.spring2go.zuul.route.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.spring2go.zuul.route.model.ZuulRouteVo;


@Service
public class ZuulRouteVoService {
    
    private static final Logger log = LoggerFactory.getLogger(ZuulRouteVoService.class);

    public static LinkedHashMap<String, ZuulRouteVo> ZUUL_ROUTE_VO_MAP = new LinkedHashMap<String, ZuulRouteVo>();

    
    public void initZuulRouteVoMap(){
        LinkedHashMap<String, ZuulRouteVo> tempMap = queryAll();
        synchronized (ZUUL_ROUTE_VO_MAP){
            ZUUL_ROUTE_VO_MAP = tempMap;
        }
    }


    /**
     * dynamic routing is cleared after production
     */
    public static void clearRouteMap(){
        synchronized (ZUUL_ROUTE_VO_MAP){
            ZUUL_ROUTE_VO_MAP.clear();
        }
    }

    public LinkedHashMap<String, ZuulRouteVo> queryAll(){
        LinkedHashMap<String, ZuulRouteVo> zuulRouteVoMap = new LinkedHashMap<String, ZuulRouteVo>();
        try {
            List<ZuulRouteVo> zuulRouteVoList = new ArrayList<ZuulRouteVo>();
            ZuulRouteVo zuulRouteVoOne = new ZuulRouteVo();
            zuulRouteVoOne.setId("clienttest");
            zuulRouteVoOne.setPath("/api/clienttest/**");
            zuulRouteVoOne.setServiceId("HELLO-CLIENT");
            zuulRouteVoList.add(zuulRouteVoOne);
            
//            ZuulRouteVo zuulRouteVoTwo = new ZuulRouteVo();
//            zuulRouteVoTwo.setId("localhost");
//            zuulRouteVoTwo.setPath("/zuul/**");
//            zuulRouteVoTwo.setUrl("http://localhost:8079");
//            zuulRouteVoList.add(zuulRouteVoTwo);
            
            ZuulRouteVo zuulRouteVoThr = new ZuulRouteVo();
            zuulRouteVoThr.setId("servertest");
            zuulRouteVoThr.setPath("/api/servertest/**");
            zuulRouteVoThr.setUrl("http://localhost:8071");
            zuulRouteVoList.add(zuulRouteVoThr);
            
            zuulRouteVoList.stream().forEach(zuulRouteVo -> {
                String path = zuulRouteVo.getPath();
                zuulRouteVoMap.put(path, zuulRouteVo);
            });
            log.info("== query dynamic routing information is successful");
        }catch (Exception e){
            log.error("== query dynamic routing information failed: " + e);
        }
        return zuulRouteVoMap;
    }

}
