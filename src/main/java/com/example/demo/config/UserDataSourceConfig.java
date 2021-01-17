package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mysql.cj.jdbc.MysqlXADataSource;

@Configuration
@MapperScan(basePackages = {"com.example.demo.mapper.UserDS*"}, 
sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class UserDataSourceConfig {
	@Value("${spring.datasource.dynamic.datasource.master.url}")
	private String url;

	@Value("${spring.datasource.dynamic.datasource.master.username}")
	private String username;

	@Value("${spring.datasource.dynamic.datasource.master.password}")
	private String password;

	@Value("${spring.datasource.type}")
	private String dataSourceClassName;

	@Bean(name = "masterDataSource")
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


	@Bean(name = "masterSqlSessionFactory")
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource)
			throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:com/example/demo/**/xml/*Mapper.xml"));
		Interceptor[] plugins = new Interceptor[1];
		plugins[0] = new PaginationInterceptor();
		sqlSessionFactoryBean.setPlugins(plugins);
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "masterSqlSessionTemplate")
	public SqlSessionTemplate masterSqlSessionTemplate(
			@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
