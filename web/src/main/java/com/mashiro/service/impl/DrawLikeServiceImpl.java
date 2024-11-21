package com.mashiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.DrawLike;
import com.mashiro.mapper.DrawLikeMapper;
import com.mashiro.mapper.DrawRecordMapper;
import com.mashiro.service.DrawLikeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DrawLikeServiceImpl extends ServiceImpl<DrawLikeMapper, DrawLike> implements DrawLikeService {

    @Resource
    private DrawLikeMapper drawLikeMapper;

    @Resource
    private DrawRecordMapper drawRecordMapper;

    @Override
    public boolean checkIfLiked(Long drawId, Integer userId) {
        QueryWrapper<DrawLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("draw_id", drawId)
                .eq("user_id", userId);
        return drawLikeMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    @Transactional
    public boolean addLike(Long drawId, Integer userId) {
        // 查询包括逻辑删除的记录
        DrawLike existingLike = drawLikeMapper.selectByUserIdAndDrawIdIncludingDeleted(userId, drawId);

        if (existingLike != null) {
            if (existingLike.getIsDeleted().equals((byte) 1)) {
                // 恢复逻辑删除的记录
                existingLike.setIsDeleted((byte) 0);
                drawLikeMapper.updateById(existingLike);
            } else {
                // 已存在未删除的点赞记录，不应再次点赞
                return false;
            }
        } else {
            // 插入新记录
            DrawLike drawLike = new DrawLike();
            drawLike.setDrawId(drawId);
            drawLike.setUserId(userId);
            drawLikeMapper.insert(drawLike);
        }

        // 更新绘画记录的点赞数
        drawRecordMapper.incrementLikeCount(drawId);

        return true;
    }

    @Override
    @Transactional
    public boolean removeLike(Long drawId, Integer userId) {
        // 查询点赞记录
        QueryWrapper<DrawLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("draw_id", drawId)
                .eq("user_id", userId);
        DrawLike existingLike = drawLikeMapper.selectOne(queryWrapper);

        if (existingLike == null) {
            // 未找到点赞记录
            return false;
        }

        // 执行逻辑删除
        drawLikeMapper.deleteById(existingLike.getId());

        // 更新绘画记录的点赞数
        drawRecordMapper.decrementLikeCount(drawId);

        return true;
    }

    @Override
    public long getLikeCount(Long drawId) {
        QueryWrapper<DrawLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("draw_id", drawId);
        return drawLikeMapper.selectCount(queryWrapper);
    }
}
