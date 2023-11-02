package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Arrays;

@TableName("t_background")
public class Background {
    /**
     * 背景图片id
     */
    @TableId
    private String backId;

    /**
     * 背景图片
     */
    private byte[] backgroundImg;

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public byte[] getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(byte[] backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public Background(String backId, byte[] backgroundImg) {
        this.backId = backId;
        this.backgroundImg = backgroundImg;
    }


    @Override
    public String toString() {
        return "Background{" +
                "backgroundImg=" + Arrays.toString(backgroundImg) +
                ", backId='" + backId + '\'' +
                '}';
    }
}
