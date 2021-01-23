package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

@Configuration
@MapperScan("com.example.demo.mapper*")
public class MybatisPlusConfig {
	@Bean(name = "master")
	@ConfigurationProperties("spring.datasource.druid.master")
	public DataSource master() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "student")
	@ConfigurationProperties("spring.datasource.druid.student")
	public DataSource student() {
		return DruidDataSourceBuilder.create().build();
	}

	/**
	 * 将数据源放入到 这个map中，注入到IoC
	 */
	@Bean
	@Primary
	public DynamicDataSource dynamicDataSource(@Qualifier("master") DataSource oneDataSource,
			@Qualifier("student") DataSource twoDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>(2);
		targetDataSources.put(DataSourceEnum.MASTER.getValue(), oneDataSource);
		targetDataSources.put(DataSourceEnum.STUDENT.getValue(), twoDataSource);
		DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
		dynamicDataSource.setDefaultTargetDataSource(oneDataSource);
		dynamicDataSource.setTargetDataSources(targetDataSources);
		return dynamicDataSource;
	}

	@Bean("sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
			throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dynamicDataSource);
		sqlSessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:com/example/demo/**/xml/*Mapper.xml"));

		MybatisConfiguration configuration = new MybatisConfiguration();
		// configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setCacheEnabled(false);
		sqlSessionFactory.setConfiguration(configuration);
		PaginationInterceptor pagination = new PaginationInterceptor();
		pagination.setDialectType("mysql");
		sqlSessionFactory.setPlugins(new Interceptor[] { // PerformanceInterceptor(),OptimisticLockerInterceptor()
				pagination // 添加分页功能
		});
		return sqlSessionFactory.getObject();
	}

	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
			throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
