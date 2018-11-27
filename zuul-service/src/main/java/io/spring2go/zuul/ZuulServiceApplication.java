package io.spring2go.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.spring2go.zuul.route.service.ZuulRouteVoService;
import io.spring2go.zuul.route.thread.MonitorThread;
import io.spring2go.zuul.route.thread.MonitorThreadControl;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
@RestController
public class ZuulServiceApplication {
    
    @Autowired
    private ZuulRouteVoService zuulRouteVoService;

    @Autowired
    private MonitorThread monitorThread;

    @Autowired
    public void  setInitialize(){
        zuulRouteVoService.initZuulRouteVoMap();

        monitorThread.setDaemon(true);
        monitorThread.start();
    }

	public static void main(String[] args) {
		SpringApplication.run(ZuulServiceApplication.class, args);
		
		MonitorThreadControl.setState(1);
	}
	
	@RequestMapping(value = "/service", method = RequestMethod.GET)
    public String running(){
        return "dynamic route good running!";
    }
}
