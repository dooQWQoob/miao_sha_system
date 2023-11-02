package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Carousel;
import com.example.mapper.CarouselMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselMapper carouselMapper;

    @ApiOperation("查询所有轮播图")
    @GetMapping("/selectAll")
    public R selectAll(){
        Base64.Decoder decoder = Base64.getDecoder();
        List<Carousel> carousels = carouselMapper.selectList(null);
        carousels.forEach(c->c.setCarouselImage(decoder.decode(c.getCarouselImage())));
        return R.ok().data("carousels",carousels);
    }

    @ApiOperation("添加轮播图")
    @PostMapping("/addCarousel")
    public R addCarousel(@RequestParam(value = "file",required = true) MultipartFile file, Integer carouselId) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(file.getBytes());
        Carousel carousel = new Carousel();
        carousel.setCarouselId(carouselId);
        carousel.setCarouselImage(encode);
        int insert = carouselMapper.insert(carousel);
        if (insert>0){
            return R.ok().data("msg","添加成功");
        }else {
            return R.error().data("msg","添加失败");
        }
    }

    @ApiOperation("修改轮播图")
    @PostMapping("/upCarousel")
    public R upCarousel(@RequestParam(value = "file",required = true) MultipartFile file, Integer carouselId) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(file.getBytes());
        UpdateWrapper<Carousel> wrapper = new UpdateWrapper<>();
        wrapper.set("carousel_image",encode)
                .eq("carousel_id",carouselId);
        int update = carouselMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("删除轮播图")
    @GetMapping("/deleteCarousel/{mid}")
    public R deleteCarousel(@PathVariable Integer mid){
        int i = carouselMapper.deleteById(mid);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("msg","删除失败");
        }
    }
}
