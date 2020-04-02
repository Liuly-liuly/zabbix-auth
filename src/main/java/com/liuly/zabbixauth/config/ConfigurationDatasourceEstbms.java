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
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = "com.chinabulker.bms.routedesign.container.estbms.mapper",sqlSessionTemplateRef = "estbmsSqlSessionTemplate" )
public class ConfigurationDatasourceEstbms {
    @Bean(name = "estbmsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.estbms")
    @Primary
    public DataSource estbmsDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "estbmsSqlSessionFactory")
    @Primary
    public SqlSessionFactory estbmsSqlSessionFactory(@Qualifier("estbmsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/estbms/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "estbmsTransactionManager")
    @Primary
    public DataSourceTransactionManager estbmsTransactionManager(@Qualifier("estbmsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean(name = "estbmsSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate estbmsSqlSessionTemplate(@Qualifier("estbmsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
