package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    User getByOpenid(String openid);

    void insert(User user);

    User getById(Long userId);

    Integer countByMap(Map map);
}
