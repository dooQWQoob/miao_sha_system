package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Cart;
import com.example.entity.Merchant;
import com.example.entity.Product;
import com.example.mapper.CartMapper;
import com.example.mapper.MerchantMapper;
import com.example.mapper.ProductMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @ApiOperation("添加购物车")
    @PostMapping(value = "/addCart",produces = {"application/json;charset=UTF-8;"})
    public R addCart(@RequestBody Cart cart){
        //根据购物车商品名称查询该商品
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_name",cart.getShopName());
        Product product = productMapper.selectOne(queryWrapper);
        //判断该商品数量是否支持加入购物车
        if (product.getMsQty() - cart.getQty() < 0){
            return R.error().data("msg","该商家商品数量不足");
        }else {
            int insert = cartMapper.insert(cart);
            if (insert>0){
                //数量充足，可以添加购物车
                UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("ms_qty",(product.getMsQty() - cart.getQty()))
                                .eq("product_id",product.getProductId());
                productMapper.update(null,updateWrapper);
                return R.ok().data("msg","添加成功");
            }else {
                return R.error().data("msg","添加失败");
            }
        }
    }

    @ApiOperation("根据用户id查询购物车")
    @GetMapping("/selectUserById")
    public R selectUserById(@RequestParam String userId, @RequestParam Integer current){
        Page<Cart> page = new Page<>(current, 5);
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        //分页查询
        cartMapper.selectPage(page,wrapper);
        List<Cart> carts = page.getRecords();
        return R.ok().data("carts",carts);
    }

    @ApiOperation("删除购物车")
    @PostMapping(value = "/deleteCart")
    public R deleteCart(@RequestParam("ids") List<Integer> ids){
        //遍历所有删除的商品，以便恢复商品数量
        for (Cart cart : cartMapper.selectBatchIds(ids)) {
            //根据购物车商家对应的 账号和商品名称 找到该商品
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("merchants_phone",cart.getMerchantsPhone())
                    .eq("product_name",cart.getShopName());
            Product product = productMapper.selectOne(queryWrapper);
            //恢复删除的商品数量
            UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("ms_qty",product.getMsQty()+cart.getQty())
                    .eq("product_id",product.getProductId());
            productMapper.update(null, updateWrapper);
        }
        //恢复完毕，删除订单
        cartMapper.deleteBatchIds(ids);
        return R.ok().data("msg","删除成功");
    }

    @ApiOperation("修改商品信息")
    @PostMapping(value = "/upCart",produces = {"application/json;charset=UTF-8;"})
    public R upCart(@RequestBody Cart cart){
        UpdateWrapper<Cart> wrapper = new UpdateWrapper<>();
        wrapper.set("qty",cart.getQty())
                .set("price",cart.getPrice())
                .eq("cart_id",cart.getCartId());
        int update = cartMapper.update(null, wrapper);
        if(update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }
}
