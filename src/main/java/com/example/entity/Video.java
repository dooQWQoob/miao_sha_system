package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_video")
public class Video {
    /**
     * 视频id
     */
    @TableId
    private Integer videoId;
    /**
     * 视频主体
     */
    private byte[] videoPojo;

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public byte[] getVideoPojo() {
        return videoPojo;
    }

    public void setVideoPojo(byte[] videoPojo) {
        this.videoPojo = videoPojo;
    }


    @Override
    public String toString() {
        return "Video{" +
                "videoId=" + videoId +
                ", videoPojo=" + videoPojo +
                '}';
    }
}
