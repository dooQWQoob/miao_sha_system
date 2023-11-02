package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Background;
import com.example.mapper.BackgroundMapper;
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
@RequestMapping("/back")
public class BackgroundController {

    @Autowired
    private BackgroundMapper backgroundMapper;

    @ApiOperation("修改图片")
    @PutMapping("/upBack")
    public R upBack(@RequestParam(value = "file",required = true) MultipartFile file,@RequestParam("backId") String backId) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(file.getBytes());
        UpdateWrapper<Background> wrapper = new UpdateWrapper<>();
        wrapper.set("background_img",encode)
                .eq("back_id",backId);
        int update = backgroundMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("添加图片")
    @PostMapping("/addBack")
    public R addBack(@RequestParam(value = "file",required = true) MultipartFile file,String backId) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(file.getBytes());
        Background background = new Background(backId, encode);
        int insert = backgroundMapper.insert(background);
        if (insert>0){
            return R.ok().data("msg","添加成功");
        }else {
            return R.error().data("msg","添加失败");
        }
    }

    @ApiOperation("查询所有图片")
    @GetMapping("/allBack")
    public R allBack(){
        List<Background> backgrounds = backgroundMapper.selectList(null);
        Base64.Decoder decoder = Base64.getDecoder();
        backgrounds.forEach(b -> b.setBackgroundImg(decoder.decode(b.getBackgroundImg())));
        return R.ok().data("backgrounds",backgrounds);
    }

    @ApiOperation("将图片设为背景")
    @GetMapping("/backImg/{imgId}")
    public R backImg(@PathVariable String imgId){
        Base64.Decoder decoder = Base64.getDecoder();
        Background background = backgroundMapper.selectById(imgId);
        background.setBackgroundImg(decoder.decode(background.getBackgroundImg()));
        return R.ok().data("msg",background);
    }

    @ApiOperation("删除背景图片")
    @GetMapping("/delete/{imgId}")
    public R delete(@PathVariable String imgId){
        int i = backgroundMapper.deleteById(imgId);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("msg","删除失败");
        }
    }
}
