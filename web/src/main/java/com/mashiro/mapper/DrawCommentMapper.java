package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.DrawComment;
import org.apache.ibatis.annotations.Update;

public interface DrawCommentMapper extends BaseMapper<DrawComment> {
    
    @Update("UPDATE draw_comment SET like_count = like_count + 1 WHERE id = #{commentId}")
    void incrLikeCount(Long commentId);
    
    @Update("UPDATE draw_comment SET like_count = like_count - 1 WHERE id = #{commentId} AND like_count > 0")
    void decrLikeCount(Long commentId);
} 