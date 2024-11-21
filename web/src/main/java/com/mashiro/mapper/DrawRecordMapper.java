package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.DrawRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DrawRecordMapper extends BaseMapper<DrawRecord> {

    @Update("UPDATE draw_record SET like_count = like_count + 1 WHERE id = #{drawId}")
    void incrementLikeCount(@Param("drawId") Long drawId);

    @Update("UPDATE draw_record SET like_count = like_count - 1 WHERE id = #{drawId} AND like_count > 0")
    int decrementLikeCount(@Param("drawId") Long drawId);

    @Select("SELECT like_count FROM draw_record WHERE id = #{drawId}")
    long selectLikeCount(@Param("drawId") Long drawId);

    @Select("SELECT COUNT(1) FROM draw_record WHERE id = #{id}")
    boolean existsById(@Param("id") Long id);

    Long getLikeCount(Long drawId);
}

