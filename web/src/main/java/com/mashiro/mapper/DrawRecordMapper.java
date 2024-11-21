package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.DrawRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DrawRecordMapper extends BaseMapper<DrawRecord> {

    /**
     * 增加绘画记录的点赞数
     *
     * @param drawId 绘画ID
     */
    @Update("UPDATE draw_record SET like_count = like_count + 1 WHERE id = #{drawId}")
    void incrementLikeCount(@Param("drawId") Long drawId);

    /**
     * 减少绘画记录的点赞数
     *
     * @param drawId 绘画ID
     */
    @Update("UPDATE draw_record SET like_count = like_count - 1 WHERE id = #{drawId} AND like_count > 0")
    void decrementLikeCount(@Param("drawId") Long drawId);
}
