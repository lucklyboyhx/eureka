package io.spring2go.zuul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.monitoring.MonitoringHelper;

@Configuration
public class GroovyLoadLineRunner implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(GroovyLoadLineRunner.class);
    
    @Value("${filter.groovy.path}")
    private String groovyPath;
    
    @Value("${filter.groovy.refreshTime}")
    private Integer refreshTime;

    @Override
    public void run(String... args) throws Exception {
        MonitoringHelper.initMocks();
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            log.info("加载过滤器:" + groovyPath);
            //每10s刷新一次
            if (refreshTime == null) {
                refreshTime = 10;
            }
            FilterFileManager.init(refreshTime, groovyPath + "pre", groovyPath + "post", groovyPath + "error", groovyPath + "route");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
}
