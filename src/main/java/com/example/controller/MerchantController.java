package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Merchant;
import com.example.entity.Product;
import com.example.mapper.MerchantMapper;
import com.example.mapper.ProductMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private ProductMapper productMapper;

    @ApiOperation("查询所有商家")
    @GetMapping("/AllMerchant")
    public R AllMerchant(){
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("permissions","merchant");
        List<Merchant> merchants = merchantMapper.selectList(wrapper);
        return R.ok().data("merchants",merchants);
    }

    @ApiOperation("添加商家")
    @PostMapping(value = "/AddMerchant",produces = {"application/json;charset=UTF-8;"})
    public R AddMerchant(@RequestBody Merchant merchant){
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("merchants_phone",merchant.getMerchantsPhone());
        Merchant selectOne = merchantMapper.selectOne(wrapper);
        if (selectOne == null){
            int insert = merchantMapper.insert(merchant);
            if (insert>0){
                return R.ok().data("msg","注册成功");
            }else {
                return R.error().data("mag","注册失败");
            }
        }else {
            return R.error().data("msg","该账户已注册商家");
        }
    }

    @ApiOperation("修改商家logo")
    @PostMapping("/updateLogo")
    public R updateLogo(@RequestParam(value = "file",required = true)MultipartFile file,String merchantsPhone) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = encoder.encode(file.getBytes());
        UpdateWrapper<Merchant> wrapper = new UpdateWrapper<>();
        wrapper.set("merchants_logo",bytes)
                .eq("merchants_phone",merchantsPhone);
        int update = merchantMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("获取商家logo")
    @GetMapping("/getLogo/{merchantsPhone}")
    public R getLogo(@PathVariable String merchantsPhone){
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("merchants_phone",merchantsPhone);
        Merchant merchant = merchantMapper.selectOne(wrapper);
        if(merchant != null){
            byte[] merchantsLogo = merchant.getMerchantsLogo();
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decode = decoder.decode(merchantsLogo);
            merchant.setMerchantsLogo(decode);
            return R.ok().data("merchant",merchant);
        }else {
            return R.error().data("msg","没有该商家");
        }
    }

    @ApiOperation("商家登录")
    @PostMapping(value = "/logonMerchant")
    public R logonMerchant(@RequestParam("merchantsPhone") String merchantsPhone,@RequestParam("merchantsPassword") String merchantsPassword){
        //根据账户及密码查询商家
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("merchants_phone",merchantsPhone)
                .eq("merchants_password",merchantsPassword);
        Merchant merchant = merchantMapper.selectOne(wrapper);
        //不为空，表示正确
        if(merchant!=null){
            //判断商家权限 merchant
            if(merchant.getPermissions().equals("merchant")){
                //判断商家logo，有：解码
                if (merchant.getMerchantsLogo()!=null){
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] decode = decoder.decode(merchant.getMerchantsLogo());
                    merchant.setMerchantsLogo(decode);
                }
                return R.ok().data("merchantObj",merchant).data("msg","登录成功为您跳转店铺后台");
            }else {
                //权限 root
                QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("permissions","merchant");
                List<Merchant> merchants = merchantMapper.selectList(queryWrapper);
                return R.ok().data("merchantObj",merchant).data("merchants",merchants);
            }

        }else {
            return R.error().data("msg","账户或密码错误");
        }
    }

    @ApiOperation("修改商家信息")
    @PostMapping(value = "/updateMerchant",produces = {"application/json;charset=UTF-8;"})
    public R updateMerchant(@RequestBody Merchant merchant){
        UpdateWrapper<Merchant> wrapper = new UpdateWrapper<>();
        wrapper.set("merchants_name",merchant.getMerchantsName())
                .set("shop_name",merchant.getShopName())
                .set("merchants_password",merchant.getMerchantsPassword())
                .set("merchants_type",merchant.getMerchantsType())
                .eq("merchants_id",merchant.getMerchantsId());
        int update = merchantMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("商家注销")
    @GetMapping("/deleteMerchant/{merchantsPhone}")
    public R deleteMerchant(@PathVariable String merchantsPhone){
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("merchants_phone",merchantsPhone);
        int delete = merchantMapper.delete(wrapper);
        if (delete>0){
            return R.ok().data("msg","注销成功");
        }else {
            return R.error().data("msg","注销失败");
        }
    }

    @ApiOperation("根据商家查询商品")
    @GetMapping("/selectMerchantAllProduct/{merchantsPhone}")
    public R selectMerchantAllProduct(@PathVariable String merchantsPhone){
        Base64.Decoder decoder = Base64.getDecoder();
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("merchants_phone",merchantsPhone);
        List<Product> products = productMapper.selectList(wrapper);
//        products.forEach(p -> p.setProductImage(decoder.decode(p.getProductImage())));
        products.forEach((p)->{
            if (p.getProductImage() != null) {
                p.setProductImage(decoder.decode(p.getProductImage()));
            }});
        return R.ok().data("products",products);
    }

    @ApiOperation("添加秒杀商品数量")
    @PostMapping(value = "/addQty",produces = {"application/json;charset=UTF-8;"})
    public R addQty(@RequestBody Product product){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchants_phone",product.getMerchantsPhone())
                    .eq("product_name",product.getProductName())
                    .eq("product_id",product.getProductId());
        Product selectOne = productMapper.selectOne(queryWrapper);
        if (selectOne.getInventory()>0){
            UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("ms_qty",selectOne.getMsQty()+selectOne.getInventory())
                         .set("inventory",0)
                         .eq("product_id",selectOne.getProductId());
            productMapper.update(null,updateWrapper);
        }
        return R.ok().data("msg","添加成功");
    }

    @ApiOperation("商家恢复")
    @PostMapping("/recovery")
    public R recovery(@RequestParam("merchantsPhone") String merchantsPhone,@RequestParam("merchantsPassword") String merchantsPassword){
        QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
        wrapper.eq("merchants_phone",merchantsPhone)
                .eq("merchants_password",merchantsPassword);
        Merchant merchant = merchantMapper.selectOne(wrapper);
        if (merchant!=null){
            return R.error().data("msg","该账户未销户");
        }else {
            Integer integer = merchantMapper.recoveryMerchant(merchantsPhone, merchantsPassword);
            if (integer>0){
                return R.ok().data("okRecover","恢复成功");
            }else {
                return R.error().data("msg","账户或密码错误");
            }
        }
    }
}
