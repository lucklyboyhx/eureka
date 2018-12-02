package io.spring2go.zuul;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = "io.spring2go.zuul.dao",
        sqlSessionFactoryRef = "sqlSessionFactory")
//@PropertySource({"classpath:application.yml"})
public class MybatisConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;

    @Value("${jdbc.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${jdbc.minPoolSize}")
    private Integer minPoolSize;

    @Value("${jdbc.initialPoolSize}")
    private Integer initialPoolSize;

    @Value("${jdbc.maxWait}")
    private Integer maxWait;

    @Value("${jdbc.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;

    @Value("${jdbc.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${jdbc.validationQuery}")
    private String validationQuery;

    @Value("${jdbc.testWhileIdle}")
    private Boolean testWhileIdle;

    @Value("${jdbc.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${jdbc.testOnReturn}")
    private Boolean testOnReturn;

    @Value("${jdbc.filters}")
    private String filters;


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapperLocations));
        return sqlSessionFactoryBean.getObject();
    }
    
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxPoolSize);
        dataSource.setMinIdle(minPoolSize);
        dataSource.setInitialSize(initialPoolSize);
        dataSource.setMaxWait(maxWait);

        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
