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
       
       
     ***********************oa_dao ***********************
     
     ﻿ <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:context="http://www.springframework.org/schema/context"
              xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context.xsd">
       
           <!--开启自动扫描-->
           <context:component-scan base-package="com.imooc.oa.dao"/>
       
           <!--配置数据源--><!--DriverManagerDataSource是Spring jdbc里提供的数据源-->
           <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
               <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
               <property name="url"
                         value="jdbc:mysql://localhost:3306/oa?useUnicode=true&amp;characterEncoding=utf-8"></property>
               <property name="username" value="root"></property>
               <property name="password" value="123456"></property>
           </bean>
       
           <!--session工厂:由mybatis-spring整合包提供-->
           <bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
               <property name="dataSource" ref="dataSource"/>
               <!--还可以配置别名:在mybatis映射文件中调用实体类：不用调用实体类全路径，只用类名-->
               <property name="typeAliasesPackage" value="com.imooc.oa.entity"/>
           </bean>
       
           <!--MapperScannerConfigurer映射器接口：被自动调用：由mybatis-spring整合包提供-->
           <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
               <property name="sqlSessionFactoryBeanName" value="sessionFactory"/>
               <property name="basePackage" value="com.imooc.oa.dao"/>
           </bean>
       </beans>
     
     
     
     ******************************oa_biz*********************************************
     
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:context="http://www.springframework.org/schema/context"
            xmlns:aop="http://www.springframework.org/schema/aop"
            xmlns:tx="http://www.springframework.org/schema/tx"
            xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context.xsd
         http://www.springframework.org/schema/aop
         http://www.springframework.org/schema/aop/spring-aop.xsd
         http://www.springframework.org/schema/tx
         http://www.springframework.org/schema/tx/spring-tx.xsd">
     
         <!--  <aop:aspectj-autoproxy/>-->
         <import resource="spring-dao.xml"/>
         <!--包扫描-->
         <context:component-scan base-package="com.imooc.oa.biz"/>
         <!--声明式事务-->
        <!--事务管理器-->
         <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
             <property name="dataSource" ref="dataSource"/>
         </bean>
     
         <!--用tx命名空间声明通知-->
         <tx:advice id="myAdvice" transaction-manager="transactionManager">
            <!--通过属性的方式去指定里边的哪些方法我们怎么去处理-->
             <tx:attributes>
                 <!--只读：不使用事务-->
                 <tx:method name="get*" read-only="true"/>
                 <tx:method name="find*" read-only="true"/>
                 <tx:method name="search*" read-only="true"/>
                 <tx:method name="*" propagation="REQUIRED"/>
             </tx:attributes>
         </tx:advice>
     
         <!--通知与切入点进行关联-->
         <aop:config>
             <aop:pointcut id="pointcut" expression="execution(* com.imooc.oa.biz.*.*(..))"/>
             <aop:advisor advice-ref="myAdvice" pointcut-ref="pointcut"/>
         </aop:config>
     
     </beans>
     
    
     ******************************oa_web*********************************************
     
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:context="http://www.springframework.org/schema/context"
            xmlns:mvc="http://www.springframework.org/schema/mvc"
            xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context.xsd
         http://www.springframework.org/schema/mvc
         http://www.springframework.org/schema/mvc/spring-mvc.xsd">
         <import resource="spring-biz.xml"/>
         <context:component-scan base-package="com.imooc.oa.controller"/>
         <!--开启mvc注解驱动，除了context:component-scan中扫描的controller、service、repository、compnent
          外-->
         <mvc:annotation-driven/>
         <mvc:default-servlet-handler/>
         <!--视图转换器-->
          <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
              <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
              <property name="prefix" value="/WEB-INF/pages"/>
              <property name="suffix" value=".jsp"/>
          </bean>
     </beans>
     
     
          ******************************web.xml*********************************************
     
      <?xml version="1.0" encoding="UTF-8"?>
      <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
               version="3.1">
      
         <!--配置springmvc加载-->
          <servlet>
              <servlet-name>DispatcherServlet</servlet-name>
              <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:spring-web.xml</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
          </servlet>
      
          <servlet-mapping>
              <servlet-name>DispatcherServlet</servlet-name>
              <url-pattern>/*</url-pattern>
          </servlet-mapping>
      
      </web-app>
