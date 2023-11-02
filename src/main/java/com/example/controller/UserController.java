package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import com.example.utils.R;
import com.mysql.cj.util.Base64Decoder;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;



    @ApiOperation("返回所有用户")
    @GetMapping("/select")
    public R userAll(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("permissions","user");
        List<User> users = userMapper.selectList(wrapper);
        Base64.Decoder decoder = Base64.getDecoder();
        //遍历用户，排除没有设置头像的用户,为有头像的用户解码
        users.forEach((u)->{if (u.getUserAvatar()!=null){
            u.setUserAvatar(decoder.decode(u.getUserAvatar()));
        }
        });
//        users.forEach(u -> u.setUserAvatar(decoder.decode(u.getUserAvatar())));
        return R.ok().data("users",users);
    }

    @ApiOperation("用户登录")
    @PostMapping(value = "/login")
    public R userLogin(@RequestParam("user_phone") String user_phone ,@RequestParam("user_password") String user_password){
        //查询该用户账户及密码
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone",user_phone);
        wrapper.eq("user_password",user_password);
        User selectOne = userMapper.selectOne(wrapper);
        //不为空，则表示该用户账户密码正确，可登录
        if(selectOne != null){
            //判断用户权限  user
            if (selectOne.getPermissions().equals("user")){
                //判断用户头像，有：解码
                if(selectOne.getUserAvatar() != null){
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] decode = decoder.decode(selectOne.getUserAvatar());
                    selectOne.setUserAvatar(decode);
                }
                return R.ok().data("userObj",selectOne).data("msg","登录成功!");
            }else{
                //用户权限 root
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("permissions","user");
                List<User> users = userMapper.selectList(queryWrapper);
                return R.ok().data("userObj",selectOne).data("users",users);
            }

        }else {
            return R.error().data("msg","没有此用户");
        }
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public R updateAvatar(@RequestParam(value = "file",required = true) MultipartFile file,String user_phone) throws IOException {
        //Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytecode = encoder.encode(file.getBytes());
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_avatar",bytecode)
                .eq("user_phone",user_phone);
        int update = userMapper.update(null,updateWrapper);
        if(update!=0){
            return R.ok().data("update",update);
        }else {
            return R.error().data("update",update);
        }
    }

    @ApiOperation("获取头像")
    @GetMapping("/getAvatar/{user_phone}")
    public R getAvatar(@PathVariable String user_phone) throws IOException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone",user_phone);
        User user = userMapper.selectOne(wrapper);
        if(user!=null){
            byte[] userAvatar = (byte[]) user.getUserAvatar();
            //Base64解码
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decoderData = decoder.decode(userAvatar);
            user.setUserAvatar(decoderData);
            return R.ok().data("image",user);
        }else {
            return R.error().data("error","没有该用户");
        }
    }

    @ApiOperation("添加用户")
    @PostMapping(value = "/addUser",produces = {"application/json;charset=UTF-8;"})
    public R addUser(@RequestBody User user){
        if(user.getUserPhone()!=null && user.getUserPassword()!=null){
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_phone",user.getUserPhone());
            User selectOne = userMapper.selectOne(wrapper);
            if (selectOne!=null){
                return R.error().data("msg","用户已存在,请前往登录");
            }else {
                int insert = userMapper.insert(user);
                return R.ok().data("msg","注册成功");
            }
        }else {
            return R.error().data("msg","请填写完整信息");
        }
    }

    @ApiOperation("修改用户信息")
    @PostMapping(value = "/updateUser",produces = {"application/json;charset=UTF-8;"})
    public R updateUser(@RequestBody User user){
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("user_phone",user.getUserPhone())
                .set("user_name",user.getUserName())
                .set("user_password",user.getUserPassword())
                .set("user_age",user.getUserAge())
                .set("user_email",user.getUserEmail())
                .eq("user_id",user.getUserId());
        int update = userMapper.update(null, wrapper);
        if(update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("用户注销")
    @GetMapping("/deleteUser/{user_phone}")
    public R deleteUser(@PathVariable String user_phone){
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_phone",user_phone);
        int delete = userMapper.delete(wrapper);
        if(delete>0){
            return R.ok().data("msg","注销成功");
        }else {
            return R.error().data("msg","注销失败");
        }
    }

    @ApiOperation("恢复用户")
    @PostMapping("/recover")
    public R recover(@RequestParam("userPhone") String userPhone,@RequestParam("userPassword") String userPassword){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone",userPhone)
                .eq("user_password",userPassword);
        User user = userMapper.selectOne(wrapper);
        if (user!=null){
            return R.error().data("msg","该用户很活跃，未销户");
        }else {
            Integer integer = userMapper.recoveryUser(userPhone, userPassword);
            if (integer>0){
                return R.ok().data("okRecover","恢复成功");
            }else {
                return R.error().data("msg","账户或密码错误");
            }
        }
    }





//授权与认证
//    @RequestMapping("/login")
//    public String toLogin(String user, String pwd, Model model){
//        Subject subject = SecurityUtils.getSubject();
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_phone",user);
//        User user1 = userMapper.selectOne(wrapper);
//        System.out.println(user1);
//        System.out.println(user+"$$$$$"+pwd);
//        UsernamePasswordToken token = new UsernamePasswordToken(user,pwd);
//        try {
//            subject.login(token);
//            return "index";
//        }catch (UnknownAccountException e){
//            model.addAttribute("msg","用户名错误");
//            return "login";
//        }catch (IncorrectCredentialsException e){
//            model.addAttribute("msg","密码错误");
//            return "login";
//        }
//    }
}
