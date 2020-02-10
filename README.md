# oa
基于springmvc-step2项目练习：办公室系统核心模块
一、项目结构

    三层架构
    -持久层--Mybatis
    -表现层--Spring MVC
    -业务层--JavaBean
    基于MVC模式
    -视图--jsp
    -模型--JavaBean
    -控制器--Spring Conroller

二、依赖 spring 4.0.2.RELEASE

     oa_dao 持久层依赖：
     mysql驱动：mysql-connector-java 5.1.44
     mybatis:3.4.4
     spring:spring-beans 、spring-context、spring-jdbc
     mybatis-spring整合相关：mybatis-spring 1.3.1
     
     oa_biz 业务层依赖：
     首先依赖oa_dao
     声明式事务：spring-tx
     spring :spring-aop 、aspectjweaver 1.8.0
     
     oa_web 表现层依赖：
     首先依赖oa_biz
     servlet依赖：javax.servlet-api 4.0.0 jstl 1.2
     SpringMVC相关依赖：spring-web spring-webmvc
     
三、包及全局配置    

     oa_dao
       -dao、entity、global
       -数据源、Session工厂、映射器接口
       
     oa_biz
       -biz
       -事务
       
     oa_web
       -controller、dto、global
       -静态资源处理、视图转换器
       -SpringMVC加载
       
       
        
      
          
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     