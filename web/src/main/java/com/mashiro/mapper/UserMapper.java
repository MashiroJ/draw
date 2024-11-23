package com.mashiro.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mashiro
 * @description 针对表【sys_user(系统用户表)】的数据库操作Mapper
 * @createDate 2024-09-24 22:13:09
 * @Entity com.mashiro.entity.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectOneByUsername(String username);

    void registerUser(User user);

    void addPoint(long userId, Integer addPoint);
}




