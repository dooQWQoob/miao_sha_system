package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 *
 * @author taozi
 * @since 2023-08-14
 */
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "orders_id", type = IdType.AUTO)
    private Integer ordersId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商家名称
     */
    private String merchantsName;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 订单数量
     */
    private Integer orderQty;

    /**
     * 订单总价格
     */
    private Double orderPrice;

    /**
     * 收货人名字
     */
    private String userName;

    /**
     *是否已送达
     */
    private String isDelivered;

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getMerchantsName() {
        return merchantsName;
    }

    public void setMerchantsName(String merchantsName) {
        this.merchantsName = merchantsName;
    }
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(String isDelivered) {
        this.isDelivered = isDelivered;
    }

    @Override
    public String toString() {
        return "Order{" +
                "createDate='" + createDate + '\'' +
                ", isDelivered='" + isDelivered + '\'' +
                ", merchantsName='" + merchantsName + '\'' +
                ", orderPrice=" + orderPrice +
                ", orderQty=" + orderQty +
                ", ordersId=" + ordersId +
                ", productId=" + productId +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
