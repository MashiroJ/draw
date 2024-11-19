package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.DrawLike;
import com.mashiro.entity.DrawRecord;
import com.mashiro.mapper.DrawLikeMapper;
import com.mashiro.mapper.DrawRecordMapper;
import com.mashiro.service.DrawLikeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DrawLikeServiceImpl extends ServiceImpl<DrawLikeMapper, DrawLike> implements DrawLikeService {

    @Resource
    private DrawLikeMapper drawLikeMapper;

    @Resource
    private DrawRecordMapper drawRecordMapper;

    /**
     * 切换点赞状态(点赞/取消点赞)
     *
     * @param drawId 绘画ID
     * @param userId 用户ID
     * @return 点赞记录
     */
    public DrawLike toggleLike(Long drawId, Integer userId) {
        // 检查是否已经点过赞
        DrawLike existingLike = drawLikeMapper.selectByDrawIdAndUserId(drawId, userId);
        if (existingLike != null) {
            // 取消点赞
            boolean isLiked = false;
            drawLikeMapper.updateIsLikedByDrawIdAndUserId(drawId, userId, isLiked);
            // 更新绘画表的 like_count 字段
            updateDrawLikeCount(drawId, -1);
            log.info("取消点赞: drawId={}, userId={}", drawId, userId);
            return null;
        } else {
            // 点赞
            DrawLike like = new DrawLike();
            like.setDrawId(drawId);
            like.setUserId(userId);
            like.setIsLiked(true); // 设置 is_liked 为 true
            drawLikeMapper.insert(like);
            // 更新绘画表的 like_count 字段
            updateDrawLikeCount(drawId, 1);
            log.info("点赞: drawId={}, userId={}", drawId, userId);
            return like;
        }
    }

    /**
     * 更新绘画表的 like_count 字段
     *
     * @param drawId    绘画ID
     * @param likeCount 点赞数变化量
     */
    private void updateDrawLikeCount(Long drawId, int likeCount) {
        // 查询绘画表的当前 like_count 值
        DrawRecord drawRecord = drawRecordMapper.selectById(drawId);
        int newLikeCount = drawRecord.getLikeCount() + likeCount;
        // 更新绘画表的 like_count 字段
        drawRecordMapper.updateDrawLikeCount(drawId, newLikeCount);
    }

    /**
     * 根据绘画ID获取点赞列表
     *
     * @param drawId 绘画ID
     * @return 点赞列表
     */
    public List<DrawLike> getLikesByDrawId(Long drawId) {
        // 获取某个绘画的点赞列表
        List<DrawLike> likes = drawLikeMapper.selectByDrawId(drawId);
        log.info("获取某个绘画的点赞列表: drawId={}, count={}", drawId, likes.size());
        return likes;
    }

    /**
     * 根据用户ID获取点赞列表
     *
     * @param userId 用户ID
     * @return 点赞列表
     */
    public List<DrawLike> getLikesByUserId(Integer userId) {
        // 获取某个用户的点赞列表
        List<DrawLike> likes = drawLikeMapper.selectByUserId(userId);
        log.info("获取某个用户的点赞列表: userId={}, count={}", userId, likes.size());
        return likes;
    }
}