package com.mashiro.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.dto.CommentDto;
import com.mashiro.entity.CommentLike;
import com.mashiro.entity.DrawComment;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.CommentLikeMapper;
import com.mashiro.mapper.DrawCommentMapper;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl extends ServiceImpl<DrawCommentMapper, DrawComment> implements CommentService {

    @Resource
    private CommentLikeMapper commentLikeMapper;

    @Override
    public void addComment(CommentDto commentDto) {
        DrawComment comment = new DrawComment();
        comment.setDrawId(commentDto.getDrawId());
        comment.setContent(commentDto.getContent());
        comment.setUserId(StpUtil.getLoginIdAsInt());
        comment.setParentId(commentDto.getParentId());
        comment.setReplyUserId(commentDto.getReplyUserId());
        save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        // 验证权限
        DrawComment comment = getById(commentId);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        
        Integer loginUserId = StpUtil.getLoginIdAsInt();
        if (!loginUserId.equals(comment.getUserId()) && !StpUtil.hasRole("admin")) {
            throw new RuntimeException("无权限删除该评论");
        }
        
        removeById(commentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeComment(Long commentId) {
        Integer loginUserId = StpUtil.getLoginIdAsInt();
        
        // 检查是否已点赞
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getCommentId, commentId)
                .eq(CommentLike::getUserId, loginUserId);
        
        if (commentLikeMapper.selectCount(wrapper) > 0) {
            throw new DrawException(ResultCodeEnum.COMMENT_EXIST_ERROR);
        }
        
        // 添加点赞记录
        CommentLike like = new CommentLike();
        like.setCommentId(commentId);
        like.setUserId(loginUserId);
        commentLikeMapper.insert(like);
        
        // 更新评论点赞数
        baseMapper.incrLikeCount(commentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeComment(Long commentId) {
        Integer loginUserId = StpUtil.getLoginIdAsInt();
        
        // 删除点赞记录
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getCommentId, commentId)
                .eq(CommentLike::getUserId, loginUserId);
        
        if (commentLikeMapper.delete(wrapper) > 0) {
            // 更新评论点赞数
            baseMapper.decrLikeCount(commentId);
        }
    }

    @Override
    public Page<DrawComment> getCommentPage(Long drawId, Integer pageNum, Integer pageSize) {
        Page<DrawComment> page = new Page<>(pageNum, pageSize);
        
        LambdaQueryWrapper<DrawComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrawComment::getDrawId, drawId)
                .orderByDesc(DrawComment::getCreateTime);
        
        return page(page, wrapper);
    }
} 