package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Video;
import com.example.mapper.VideoMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoMapper videoMapper;

    @ApiOperation("修改视频")
    @PutMapping("/upvideo")
    public R upvideo(@RequestParam(value = "file",required = true)MultipartFile file,Integer videoId) throws IOException {
        byte[] bytes = file.getBytes();
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(bytes);
        System.out.println("*********************"+encode);
        UpdateWrapper<Video> wrapper = new UpdateWrapper<>();
        wrapper.set("video_pojo",encode)
                .eq("video_id",videoId);
        int update = videoMapper.update(null, wrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("根据id获取视频")
    @GetMapping("/getVideo/{videoId}")
    @ResponseBody
    public void getVideo(HttpServletRequest request,HttpServletResponse response,@PathVariable Integer videoId) throws IOException {
        Video video = videoMapper.selectById(videoId);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(video.getVideoPojo());
        video.setVideoPojo(decode);


    //反流(方法的返回参数必须是void)
//        读取指定路径下面的文件
//        InputStream in = new FileInputStream(String.valueOf(decode));
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decode);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        response.reset();
//        response.setContentType("application/ostet-stream");
        //创建存放文件内容的数组
        //所读取的内容使用n来接收
        int n=0;
        //当没有读取完时,继续读取,循环
        while((n=byteArrayInputStream.read())!=-1){
            //将字节数组的数据全部写入到输出流中
            outputStream.write(decode,0,n);
        }

        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
//        in.close();
    }
}
