package io.spring2go.zuul.route.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.spring2go.zuul.route.service.RefreshRouteService;


@Service
public class MonitorThread extends Thread{
    
    @Value("${routeRefresh.time}")
    private long refreshTime;

    @Autowired
    private RefreshRouteService refreshRouteService;

    @Override
    public void run(){
        while (true){
            int state = MonitorThreadControl.getState();
            if(state == 0)
                continue;

            if(state < 0){
                return;
            }

            try {
                // do some
//                log.info("========= monitor running ==========");

                // ensure that the last route refresh is completed
                refreshRouteService.refreshRoute();

            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(refreshTime * (60 * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
