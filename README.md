# DynamicDataSourceDemo

master分支为 最普通的多数据源切换，使用mybatisplus自带的DS注解


transaction1  分支为分布式事务解决方案一：  使用Automix，为不同的数据源编辑独立的config(根据mapper所在的不同目录)，DataSourceType使用DruidXADataSource
              优点：方便快捷，适合目录结构严谨且数据库源少的情况
              缺点：比较死板，都提前定死了



transaction2  分支为分布式解决方案二：   使用jta+automix 自定义数据源注解， 重写sessiontemplate
              优点：灵活
              缺点：配置复杂
