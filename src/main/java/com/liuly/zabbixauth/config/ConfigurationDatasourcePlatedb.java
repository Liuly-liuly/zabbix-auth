package com.liuly.zabbixauth.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Description: li.liu02@hand-china.com<br/>
 * date: 2019/8/23 9:59<br/>
 *
 * @author Liuly<br />
 * @version 1.0
 * @since JDK 1.8
 */
@Configuration
@MapperScan(basePackages = "com.chinabulker.bms.routedesign.container.mapper.platedb",sqlSessionTemplateRef = "platedbSqlSessionTemplate" )
public class ConfigurationDatasourcePlatedb {
    @Bean(name = "platedbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.platedb")
    public DataSource platedbDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "platedbSqlSessionFactory")
    public SqlSessionFactory platedbSqlSessionFactory(@Qualifier("platedbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/platedb/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "platedbTransactionManager")
    public DataSourceTransactionManager platedbTransactionManager(@Qualifier("platedbDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean(name = "platedbSqlSessionTemplate")
    public SqlSessionTemplate platedbSqlSessionTemplate(@Qualifier("platedbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
