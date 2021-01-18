package com.example.demo.config;


import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mysql.cj.jdbc.MysqlXADataSource;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
@Configuration
@MapperScan(basePackages = {"com.example.demo.mapper*"}, sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisPlusConfiguration {
 
  @Value("${spring.datasource.dynamic.datasource.master.url}")
  private String url;

  @Value("${spring.datasource.dynamic.datasource.master.username}")
  private String username;

  @Value("${spring.datasource.dynamic.datasource.master.password}")
  private String password;

  @Value("${spring.datasource.dynamic.datasource.master.driver-class-name}")
  private String driverClassName;
  
  @Value("${spring.datasource.dynamic.datasource.student.url}")
  private String url2;

  @Value("${spring.datasource.dynamic.datasource.student.username}")
  private String username2;

  @Value("${spring.datasource.dynamic.datasource.student.password}")
  private String password2;

  @Value("${spring.datasource.dynamic.datasource.student.driver-class-name}")
  private String driverClassName2;

  @Value("${spring.datasource.type}")
  private String dataSourceClassName;
  
  @Bean(name = "masterDataSource")
  @Primary
  public AtomikosDataSourceBean masterDataSource() {
      AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
      sourceBean.setUniqueResourceName("masterDataSource");
      sourceBean.setXaDataSourceClassName(dataSourceClassName);
      sourceBean.setTestQuery("select 1");
      sourceBean.setBorrowConnectionTimeout(3);
      MysqlXADataSource dataSource = new MysqlXADataSource();
      dataSource.setUser(username);
      dataSource.setPassword(password);
      dataSource.setUrl(url);
      sourceBean.setXaDataSource(dataSource);
      return sourceBean;
  }
  
  @Bean(name = "studentDataSource")
  public AtomikosDataSourceBean studentDataSource() {
      AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
      sourceBean.setUniqueResourceName("studentDataSource");
      sourceBean.setXaDataSourceClassName(dataSourceClassName);
      sourceBean.setTestQuery("select 1");
     // sourceBean.setBorrowConnectionTimeout(3);
      MysqlXADataSource dataSource = new MysqlXADataSource();
      dataSource.setUser(username2);
      dataSource.setPassword(password2);
      dataSource.setUrl(url2);
      sourceBean.setXaDataSource(dataSource);
      return sourceBean;
  }
 
    @Bean(name = "sqlSessionTemplate")
    public CustomSqlSessionTemplate  customSqlSessionTemplate() throws Exception {
        Map<String, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<String, SqlSessionFactory>() {{
            put(DataSourceKeyEnum.MASTER.getValue(), createSqlSessionFactory(masterDataSource()));
            put(DataSourceKeyEnum.STUDENT.getValue(), createSqlSessionFactory(studentDataSource()));
        }};
        CustomSqlSessionTemplate  sqlSessionTemplate = new CustomSqlSessionTemplate (sqlSessionFactoryMap.get(DataSourceKeyEnum.MASTER.getValue()));
        sqlSessionTemplate.setTargetSqlSessionFactorys(sqlSessionFactoryMap);
        return sqlSessionTemplate;
    }
 
    /**
     * 创建数据源
     *
     * @param dataSource
     * @return
     */
    private SqlSessionFactory createSqlSessionFactory(AtomikosDataSourceBean dataSource) throws Exception {
  
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/example/demo/**/xml/*Mapper.xml"));
  
        
        
        //手动设置session工厂时，需要手动添加分页插件
        Interceptor[] plugins = new Interceptor[1];
        plugins[0] = new PaginationInterceptor();
        sqlSessionFactory.setPlugins(plugins);
        
        //重写了GlobalConfig的MyGlobalConfig注入到sqlSessionFactory使其生效
        MyGlobalConfig globalConfig = new MyGlobalConfig();
        sqlSessionFactory.setGlobalConfig(globalConfig);
 
        return sqlSessionFactory.getObject();
    }
 
    
}
