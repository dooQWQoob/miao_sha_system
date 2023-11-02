package com.example.config;

import com.example.entity.User;
import com.example.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        //获取当前对象
//        Subject subject = SecurityUtils.getSubject();
//        User user = (User) subject.getPrincipal();
//        //设置角色
//        HashSet<String> hashSet = new HashSet<>();
//        hashSet.add(user.getPermissions());
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(hashSet);
//        //设置权限
//        info.addStringPermission(user.getPermissions());
//        return info;
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        User user = userService.selectOneUser(token.getUsername());
//        System.out.println(user);
//        if(user != null){
//            return new SimpleAuthenticationInfo(user,user.getUserPassword(),getName());
//        }
        return null;
    }
}
