package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
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
public interface UserMapper extends BaseMapper<User> {
    Integer recoveryUser(String userPhone,String userPassword);
}
