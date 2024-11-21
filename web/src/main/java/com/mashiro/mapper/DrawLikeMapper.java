package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.DrawLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DrawLikeMapper extends BaseMapper<DrawLike> {

    DrawLike selectByUserIdAndDrawIdForUpdate(@Param("userId") Integer userId, @Param("drawId") Long drawId);

    void updateByIdAndIsDeleted(Long id);

    void updateByIdAndNotDeleted(Long id);

    DrawLike selectByUserIdAndDrawId(Integer userId, Long drawId);
}



