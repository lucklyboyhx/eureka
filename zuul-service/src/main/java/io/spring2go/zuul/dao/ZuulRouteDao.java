package io.spring2go.zuul.dao;

import java.util.List;

import io.spring2go.zuul.route.model.ZuulRouteVo;

public interface ZuulRouteDao {
    
    List<ZuulRouteVo> list();
    

}
