package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.DrawLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DrawLikeMapper extends BaseMapper<DrawLike> {
    DrawLike selectByDrawIdAndUserId(Long drawId, Integer userId);

    List<DrawLike> selectByDrawId(Long drawId);

    List<DrawLike> selectByUserId(Integer userId);
}
