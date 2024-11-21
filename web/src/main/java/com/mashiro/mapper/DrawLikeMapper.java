package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.DrawLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DrawLikeMapper extends BaseMapper<DrawLike> {
    /**
     * 查询包括逻辑删除的点赞记录
     *
     * @param userId 用户ID
     * @param drawId 绘画ID
     * @return 点赞记录
     */
    @Select("SELECT * FROM draw_like WHERE draw_id = #{drawId} AND user_id = #{userId} LIMIT 1")
    DrawLike selectByUserIdAndDrawIdIncludingDeleted(@Param("userId") Integer userId, @Param("drawId") Long drawId);
}
