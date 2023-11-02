package com.example.config.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum PayEnum {
    YESPAY(1,"已支付"),
    NOPAY(2,"未支付");
    @EnumValue
    private Integer payState;
    private String payStateName;

    PayEnum(Integer payState, String payStateName) {
        this.payState = payState;
        this.payStateName = payStateName;
    }
}
