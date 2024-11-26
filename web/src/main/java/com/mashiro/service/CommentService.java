package com.mashiro.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashiro.dto.CommentDto;
import com.mashiro.entity.DrawComment;

public interface CommentService {
    // 添加评论
    void addComment(CommentDto commentDto);
    
    // 删除评论
    void deleteComment(Long commentId);
    
    // 点赞评论
    void likeComment(Long commentId);
    
    // 取消点赞
    void unlikeComment(Long commentId);
    
    // 分页获取评论列表
    Page<DrawComment> getCommentPage(Long drawId, Integer pageNum, Integer pageSize);
} 