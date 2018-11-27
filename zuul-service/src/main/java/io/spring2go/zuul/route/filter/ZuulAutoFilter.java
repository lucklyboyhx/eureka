package io.spring2go.zuul.route.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态路由；　过滤＋动态路由
 */
public class ZuulAutoFilter extends ZuulFilter{

    private static final Logger log = LoggerFactory.getLogger(ZuulAutoFilter.class);

    /**
     * pre：可以在请求被路由之前调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();

        String uri =request.getRequestURI();

        if(uri == null){
            rc.setResponseStatusCode(404);
            rc.setSendZuulResponse(false);
            log.error("service does not exist：" + uri) ;
        }else {
            log.error("请求路由：" + uri) ;
        }
        return null;
    }
}
