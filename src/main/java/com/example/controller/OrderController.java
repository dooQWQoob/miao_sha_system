package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Merchant;
import com.example.entity.Order;
import com.example.entity.Product;
import com.example.entity.User;
import com.example.mapper.MerchantMapper;
import com.example.mapper.OrderMapper;
import com.example.mapper.ProductMapper;
import com.example.mapper.UserMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private ProductMapper productMapper;

    @ApiOperation("添加订单")
    @PostMapping(value = "/addOrder",produces = {"application/json;charset=UTF-8;"})
    public R addOrder(@RequestBody Order order){
        //根据订单对应的用户id查询用户
        QueryWrapper<User> userQW= new QueryWrapper<>();
        userQW.eq("user_id",order.getUserId());
        User user = userMapper.selectOne(userQW);
        //判断用户余额是否能够扣除订单金额
        if(user.getBalance() - order.getOrderPrice()<0){
            return R.error().data("msg","您的余额不足,还差"+(order.getOrderPrice()*order.getOrderQty()-user.getBalance())+"元");
        }else {
            //生成订单
            int insert = orderMapper.insert(order);
            if (insert>0){
                //用户余额扣除
                UpdateWrapper<User> wrapper = new UpdateWrapper<>();
                wrapper.set("balance",(user.getBalance()-order.getOrderPrice()))
                        .eq("user_id",user.getUserId());
                userMapper.update(null,wrapper);
                return R.ok().data("msg","抢购成功");
            }else {
                return R.error().data("msg","下单失败");
            }
        }
    }

    @ApiOperation("订单查询")
    @GetMapping("/selectUserOrder/{userId}")
    public R selectUserOrder(@PathVariable String userId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delivered","在途中")
                .eq("user_id",userId);
        List<Order> orders = orderMapper.selectList(wrapper);
        return R.ok().data("orders",orders);
    }

    @ApiOperation("用户确认收货")
    @GetMapping("/isDelivered/{orders_id}")
    public R isDelivered(@PathVariable Integer orders_id){
        //获取该订单
        Order order = orderMapper.selectById(orders_id);
        //根据订单id获取对应商品信息
        Product product = productMapper.selectById(order.getProductId());
        //根据商品信息获取商家信息
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchants_phone",product.getMerchantsPhone());
        Merchant merchant = merchantMapper.selectOne(queryWrapper);
        //用户确认收货，商家余额上涨
        UpdateWrapper<Merchant> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("balance",merchant.getBalance()+order.getOrderPrice())
                     .eq("merchants_id",merchant.getMerchantsId());
        merchantMapper.update(null,updateWrapper);
        //用户成功收货，修改 is_delivered 字段
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.set("is_delivered","已送达")
                .eq("orders_id",orders_id);
        int update = orderMapper.update(null, wrapper);
        return R.ok().data("msg","收货成功");
    }

    @ApiOperation("查询未收货订单")
    @GetMapping("/onDelivered/{userId}")
    public R onDelivered(@PathVariable String userId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId)
                .and(i ->i.isNull("is_delivered"));
        List<Order> orders = orderMapper.selectList(wrapper);
        return R.ok().data("orders",orders);
    }
}
