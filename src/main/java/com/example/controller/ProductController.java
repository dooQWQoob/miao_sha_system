package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Product;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @ApiOperation("添加商品")
    @PostMapping(value = "/addProduct",produces = {"application/json;charset=UTF-8;"})
    public R addProduct(@RequestBody Product product){
        System.out.println(product.getProductImage());
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        //判断商品是否已被商家添加
        wrapper.eq("product_name",product.getProductName())
                .eq("merchants_phone",product.getMerchantsPhone());
        Product selectOne = productMapper.selectOne(wrapper);
        if(selectOne!=null){
            return R.error().data("msg","该商品您已经添加");
        }else {
            //条件不满足，则表示该商品没有商家添加 或者 该商家没有该商品 可以添加
            int insert = productMapper.insert(product);
            if (insert>0){
                return R.ok().data("msg","添加成功");
            }else {
                return R.error().data("msg","添加失败");
            }
        }
    }

    @ApiOperation("查询商品")
    @GetMapping("/getProduct/{merchantsPhone}")
    public R getProduct(@PathVariable String merchantsPhone){
        Base64.Decoder decoder = Base64.getDecoder();
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("merchants_phone",merchantsPhone);
        List<Product> products = productMapper.selectList(wrapper);
        //遍历图片并解码
        products.forEach(p -> p.setProductImage(decoder.decode(p.getProductImage())));
        return R.ok().data("products",products);
    }

    @ApiOperation("根据商品名称查询商品(模糊查询)")
    @GetMapping("/getProductByName/{productName}")
    public R getProductByName(@PathVariable String productName){
        Base64.Decoder decoder = Base64.getDecoder();
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like("product_name",productName);
        List<Product> products = productMapper.selectList(wrapper);
        products.forEach(p -> p.setProductImage(decoder.decode(p.getProductImage())));
        return R.ok().data("products",products);
    }

    @ApiOperation("根据商品id查询")
    @GetMapping("/getProductById/{productId}")
    public R getProductById(@PathVariable String productId){
        Base64.Decoder decoder = Base64.getDecoder();
        Product product = productMapper.selectById(productId);
        product.setProductImage(decoder.decode(product.getProductImage()));
        return R.ok().data("product",product);
    }

    @ApiOperation("修改商品图片")
    @PostMapping ("/upImage")
    public R upImage(@RequestParam(value = "file",required = true)MultipartFile file, String id) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(file.getBytes());
        UpdateWrapper<Product> wrapper = new UpdateWrapper<>();
        wrapper.set("product_image",encode)
                .eq("product_id",id);
        int update = productMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.ok().data("msg","修改失败");
        }
    }

    @ApiOperation("修改商品信息")
    @PostMapping(value = "/upProduct",produces = {"application/json;charset=UTF-8;"})
    public R upProduct(@RequestBody Product product){
        UpdateWrapper<Product> wrapper = new UpdateWrapper<>();
        wrapper.set("original_price",product.getOriginalPrice())
                .set("ms_price",product.getMsPrice())
                .set("description",product.getDescription())
                .eq("product_id",product.getProductId());
        int update = productMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("删除商品")
    @GetMapping("/deleteProduct/{productId}")
    public R deleteProduct(@PathVariable String productId){
        productMapper.deleteById(productId);
        return R.ok().data("msg","删除成功");
    }

    @ApiOperation("根据商品类型显示")
    @GetMapping("/classify/{shopType}")
    public R classify(@PathVariable String shopType){
        Base64.Decoder decoder = Base64.getDecoder();
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("shop_type",shopType);
        List<Product> products = productMapper.selectList(wrapper);
        products.forEach(p -> p.setProductImage(decoder.decode(p.getProductImage())));
        return R.ok().data("products",products);
    }

    @ApiOperation("根据价格查询")
    @PostMapping("/underPrice")
    public R underPrice(@RequestParam("leprice") Double leprice,@RequestParam("rgprice") Double rgprice){
        Base64.Decoder decoder = Base64.getDecoder();
        if (leprice > rgprice && leprice!=0 && rgprice!=0){
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.ge("ms_price",rgprice)
                    .le("ms_price",leprice);
            List<Product> products = productMapper.selectList(wrapper);
            products.forEach(p -> p.setProductImage(decoder.decode(p.getProductImage())));
            return R.ok().data("products",products);
        }else if (leprice==0 && rgprice!=0){
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.le("ms_price",rgprice);
            List<Product> products = productMapper.selectList(wrapper);
            products.forEach(p -> p.setProductImage(decoder.decode(p.getProductImage())));
            return R.ok().data("products",products);
        }else if (leprice!=0 && rgprice==0){
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.ge("ms_price",leprice);
            List<Product> products = productMapper.selectList(wrapper);
            products.forEach(p -> p.setProductImage(decoder.decode(p.getProductImage())));
            return R.ok().data("products",products);
        }else if (leprice < rgprice && leprice!=0 && rgprice!=0){
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.ge("ms_price",leprice)
                    .le("ms_price",rgprice);
            List<Product> products = productMapper.selectList(wrapper);
            products.forEach(p -> p.setProductImage(decoder.decode(p.getProductImage())));
            return R.ok().data("products",products);
        }else {
            return R.ok().data("products","输入数据有误");
        }
    }
}
