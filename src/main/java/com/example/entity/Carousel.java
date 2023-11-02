package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Arrays;

@TableName("t_carousel")
public class Carousel implements Serializable {
    /**
     * 轮播图id
     */
    @TableId("carousel_id")
    private Integer carouselId;
    /**
     * 轮播图
     */
    private byte[] carouselImage;

    public Integer getCarouselId() {
        return carouselId;
    }

    public void setCarouselId(Integer carouselId) {
        this.carouselId = carouselId;
    }

    public byte[] getCarouselImage() {
        return carouselImage;
    }

    public void setCarouselImage(byte[] carouselImage) {
        this.carouselImage = carouselImage;
    }

    @Override
    public String toString() {
        return "Carousel{" +
                "carouselId=" + carouselId +
                ", carouselImage=" + Arrays.toString(carouselImage) +
                '}';
    }
}
