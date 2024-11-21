package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.DrawLike;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.DrawLikeMapper;
import com.mashiro.mapper.DrawRecordMapper;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.DrawLikeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DrawLikeServiceImpl extends ServiceImpl<DrawLikeMapper, DrawLike> implements DrawLikeService {

    public static final byte DELETED = 1;
    private static final String LIKE_COUNT_KEY_PREFIX = "draw:likeCount:";
    private static final String USER_LIKE_KEY_PREFIX = "draw:userLike:";

    @Resource
    private DrawLikeMapper drawLikeMapper;

    @Resource
    private DrawRecordMapper drawRecordMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleLike(Long drawId, Integer userId) {

        // 参数校验
        if (userId == null || drawId == null || drawId <= 0) {
            throw new IllegalArgumentException("参数无效");
        }

        // 验证绘画是否存在
        if (!drawRecordMapper.existsById(drawId)) {
            throw new DrawException(ResultCodeEnum.DRAW_NOT_EXISTS);
        }

        // 查询并锁定点赞记录，包含逻辑删除记录
        DrawLike existingLike = drawLikeMapper.selectByUserIdAndDrawIdForUpdate(userId, drawId);


        if (existingLike != null) {
            if (existingLike.getIsDeleted() != null && existingLike.getIsDeleted() == DELETED) {
                // 恢复点赞
                Long id = existingLike.getId();
                drawLikeMapper.updateByIdAndIsDeleted(id);
                drawRecordMapper.incrementLikeCount(drawId);
                return true; // 点赞成功
            } else {
                Long id = existingLike.getId();
                drawLikeMapper.updateByIdAndNotDeleted(id);
                int decrementResult = drawRecordMapper.decrementLikeCount(drawId);
                if (decrementResult < 0) {
                    throw new RuntimeException("点赞总数异常");
                }
                return false; // 取消点赞成功
            }
        } else {
            // 新增点赞记录
            DrawLike drawLike = new DrawLike();
            drawLike.setDrawId(drawId);
            drawLike.setUserId(userId);
            try {
                drawLikeMapper.insert(drawLike);
                drawRecordMapper.incrementLikeCount(drawId);
                return true; // 点赞成功
            } catch (DuplicateKeyException e) {
                throw new RuntimeException("重复的点赞记录");
            }
        }
    }


    @Override
    public long getLikeCount(Long drawId) {
        log.info("获取绘画点赞数 - 绘画ID: {}", drawId);
        // 可选：在这里添加缓存
        long likeCount = drawRecordMapper.selectLikeCount(drawId);
        log.info("获取到的绘画点赞数 - 绘画ID: {}, 点赞数: {}", drawId, likeCount);
        return likeCount;
    }
}
