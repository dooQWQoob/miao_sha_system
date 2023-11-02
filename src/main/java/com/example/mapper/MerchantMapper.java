package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Merchant;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author taozi
 * @since 2023-08-14
 */
@Repository
public interface MerchantMapper extends BaseMapper<Merchant> {
    Integer recoveryMerchant(String merchantsPhone,String merchantsPassword);
}
