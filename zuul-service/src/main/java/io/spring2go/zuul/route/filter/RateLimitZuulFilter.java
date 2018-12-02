package io.spring2go.zuul.route.filter;

import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.spring2go.zuul.route.service.RateLimitService;

public class RateLimitZuulFilter extends ZuulFilter{
    
    private static final Logger log = LoggerFactory.getLogger(RateLimitZuulFilter.class);

    private Map<String, RateLimiter> map = RateLimitService.MAP;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 这边的order一定要大于org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter的order
        // 也就是要大于5
        // 否则，RequestContext.getCurrentContext()里拿不到serviceId等数据。
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        // 这里可以考虑弄个限流开启的开关，开启限流返回true，关闭限流返回false，你懂的。
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext context = RequestContext.getCurrentContext();
            HttpServletResponse response = context.getResponse();

            String key = null;
            // 对于service格式的路由，走RibbonRoutingFilter
            String serviceId = (String) context.get("serviceId");
            if (serviceId != null) {
                key = serviceId;
//                if (map.get(key) == null) {
//                    map.putIfAbsent(serviceId, RateLimiter.create(100.0));
//                    
//                }
            }
            // 如果压根不走RibbonRoutingFilter，则认为是URL格式的路由
            else {
                // 对于URL格式的路由，走SimpleHostRoutingFilter
                URL routeHost = context.getRouteHost();
                if (routeHost != null) {
                    String url = routeHost.toString();
                    key = url;
//                    map.putIfAbsent(url, RateLimiter.create(10.0));
                }
            }
            RateLimiter rateLimiter = map.get(key);
            if (!rateLimiter.tryAcquire()) {
                
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>请求拒绝");
                HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;

                synchronized (response) {
                    
                    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                    response.setStatus(httpStatus.value());
//                    response.getWriter().append(httpStatus.getReasonPhrase());
                }

                context.setSendZuulResponse(false);

                throw new ZuulException(
                        httpStatus.getReasonPhrase(),
                        httpStatus.value(),
                        httpStatus.getReasonPhrase()
                );
            }
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }
}
