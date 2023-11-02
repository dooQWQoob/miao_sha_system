package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 *
 * @author taozi
 * @since 2023-08-14
 */
@TableName("t_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private byte[] productImage;

    /**
     * 所属商家
     */
    private String merchantsPhone;

    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 秒杀价
     */
    private Double msPrice;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 秒杀商品数
     */
    private Integer msQty;

    /**
     * 剩余库存
     */
    private Integer inventory;

    /**
     * 商品类型
     */
    private String shopType;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否显示
     * @return
     */
    private String showMark;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }
    public String getMerchantsPhone() {
        return merchantsPhone;
    }

    public void setMerchantsPhone(String merchantsPhone) {
        this.merchantsPhone = merchantsPhone;
    }
    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }
    public Double getMsPrice() {
        return msPrice;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public Integer getMsQty() {
        return msQty;
    }

    public void setMsQty(Integer msQty) {
        this.msQty = msQty;
    }
    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public void setMsPrice(Double msPrice) {
        this.msPrice = msPrice;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String describe) {
        this.description = describe;
    }

    public String getShowMark() {
        return showMark;
    }

    public void setShowMark(String showMark) {
        this.showMark = showMark;
    }


    @Override
    public String toString() {
        return "Product{" +
                "beginDate='" + beginDate + '\'' +
                ", description='" + description + '\'' +
                ", endDate='" + endDate + '\'' +
                ", inventory=" + inventory +
                ", merchantsPhone='" + merchantsPhone + '\'' +
                ", msPrice=" + msPrice +
                ", msQty=" + msQty +
                ", originalPrice=" + originalPrice +
                ", productId='" + productId + '\'' +
                ", productImage=" + Arrays.toString(productImage) +
                ", productName='" + productName + '\'' +
                ", shopType='" + shopType + '\'' +
                ", showMark='" + showMark + '\'' +
                '}';
    }
}
