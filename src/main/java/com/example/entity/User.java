package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.config.enums.SexEnum;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author taozi
 * @since 2023-08-14
 */
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId
    private String userId;

    /**
     * 用户名称(收货人)
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userPhone;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户性别（枚举）
     */
    private SexEnum userSex;

    /**
     * 用户年龄
     */
    private Integer userAge;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户头像
     */

//@Lob 通常与@Basic同时使用，提高访问速度
//    @Lob
//    @Basic(fetch = FetchType.LAZY)
    private byte[] userAvatar;


    /**
     * 用户余额
     */
    private Double balance;

    /**
     * 注册时间
     */
    private String enrollDate;

    /**
     * 用户权限
     */
    private String permissions;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField("isDelete")
    private Integer isDelete;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public SexEnum getUserSex() {return userSex;}

    public void setUserSex(SexEnum userSex) {
        this.userSex = userSex;
    }
    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public Double getBalance() {
        return balance;
    }

    public byte[] getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(byte[] userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }
    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


    @Override
    public String toString() {
        return "User{" +
                "balance=" + balance +
                ", enrollDate=" + enrollDate +
                ", isDelete=" + isDelete +
                ", permissions='" + permissions + '\'' +
                ", userAge=" + userAge +
                ", userAvatar=" + userAvatar +
                ", userEmail='" + userEmail + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userSex=" + userSex +
                '}';
    }
}
