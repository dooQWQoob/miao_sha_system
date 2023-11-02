package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 *
 * @author taozi
 * @since 2023-08-14
 */
@TableName("t_merchant")
public class Merchant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商家id
     */
    @TableId
    private String merchantsId;

    /**
     * 商家名称
     */
    private String merchantsName;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商家账号
     */
    private String merchantsPhone;

    /**
     * 商家密码
     */
    private String merchantsPassword;

    /**
     * 商家logo
     */
    private byte[] merchantsLogo;

    /**
     * 商家余额
     */
    private Double balance;

    /**
     * 注册时间
     */
    private String enrollDate;

    /**
     * 商家经营类型(方便用户定位，快速找到)
     */
    private String merchantsType;

    /**
     * 商家权限
     */
    private String permissions;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField("isDelete")
    private Integer isDelete;

    public String getMerchantsId() {
        return merchantsId;
    }

    public void setMerchantsId(String merchantsId) {
        this.merchantsId = merchantsId;
    }
    public String getMerchantsName() {
        return merchantsName;
    }

    public void setMerchantsName(String merchantsName) {
        this.merchantsName = merchantsName;
    }
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getMerchantsPhone() {
        return merchantsPhone;
    }

    public void setMerchantsPhone(String merchantsPhone) {
        this.merchantsPhone = merchantsPhone;
    }
    public String getMerchantsPassword() {
        return merchantsPassword;
    }

    public void setMerchantsPassword(String merchantsPassword) {
        this.merchantsPassword = merchantsPassword;
    }
    public Double getBalance() {
        return balance;
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
    public String getMerchantsType() {
        return merchantsType;
    }

    public void setMerchantsType(String merchantsType) {
        this.merchantsType = merchantsType;
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

    public byte[] getMerchantsLogo() {
        return merchantsLogo;
    }

    public void setMerchantsLogo(byte[] merchantsLogo) {
        this.merchantsLogo = merchantsLogo;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "balance=" + balance +
                ", enrollDate=" + enrollDate +
                ", isDelete=" + isDelete +
                ", merchantsId='" + merchantsId + '\'' +
                ", merchantsLogo=" + Arrays.toString(merchantsLogo) +
                ", merchantsName='" + merchantsName + '\'' +
                ", merchantsPassword='" + merchantsPassword + '\'' +
                ", merchantsPhone='" + merchantsPhone + '\'' +
                ", merchantsType='" + merchantsType + '\'' +
                ", permissions='" + permissions + '\'' +
                ", shopName='" + shopName + '\'' +
                '}';
    }
}
