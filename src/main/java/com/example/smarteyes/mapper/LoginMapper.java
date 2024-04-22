package com.example.smarteyes.mapper;

import com.example.smarteyes.domain.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 黄一峰
 * @date 2024/4/16 14:01
 * @description LoginMapper
 */
@Mapper
public interface LoginMapper {
    User selectUserByName(String userName);
}
