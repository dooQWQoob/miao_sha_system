package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;

/**
 *
 * @author taozi
 * @since 2023-08-14
 */
@TableName("t_cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车id
     */
    @TableId(value = "cart_id",type = IdType.AUTO)
    private Integer cartId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 所属商家
     */
    private String merchantsPhone;

    /**
     * 商品名称
     */
    private String shopName;

    /**
     * 商品图片
     */
    private byte[] productImage;

    /**
     * 购物车数量
     */
    private Integer qty;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 总价格
     */
    private Double price;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getMerchantsPhone() {
        return merchantsPhone;
    }

    public void setMerchantsPhone(String merchantsPhone) {
        this.merchantsPhone = merchantsPhone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", merchantsPhone='" + merchantsPhone + '\'' +
                ", price=" + price +
                ", productId=" + productId +
                ", productImage=" + Arrays.toString(productImage) +
                ", qty=" + qty +
                ", shopName='" + shopName + '\'' +
                ", unitPrice=" + unitPrice +
                ", userId='" + userId + '\'' +
                '}';
    }
}
