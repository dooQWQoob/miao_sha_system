package com.example.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig{

    //ShiroFilterFactoryBean 3.
//    @Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("DWM") DefaultWebSecurityManager manager){
//        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
//        //设置安全管理器
//        bean.setSecurityManager(manager);
//        //添加shiro的内置过滤器
//        /*
//            anon:无需认证就可访问
//            authc：必须认证才能访问
//            user：必须拥有 记住我 功能才能访问
//            perms：拥有对某个类资源的权限才能访问
//            role：拥有某个角色权限才能访问
//         */
//        Map<String, String> filterMap = new LinkedHashMap<>();
//        //进行拦截
////        filterMap.put("/user/login","anon");
////        filterMap.put("/user/select","perms[root]");
////        bean.setFilterChainDefinitionMap(filterMap);
//        //设置登录请求
////        bean.setLoginUrl("/toLogin");
//        //未授权页面
////        bean.setUnauthorizedUrl("/toLogin");
//        return bean;
//    }
//    //DaFaultWebSecurityManager 2.
//    @Bean(name = "DWM")
//    public DefaultWebSecurityManager getDeFaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm){
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        //关联 MyRealm
//        securityManager.setRealm(myRealm);
//        return securityManager;
//    }
//    //创建realm 对象，需要自定义类 1.
//    @Bean
//    public MyRealm myRealm(){
//        return new MyRealm();
//    }
}
