package com.mashiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashiro.entity.DrawLike;

public interface DrawLikeService extends IService<DrawLike> {

    /**
     * 检查用户是否已点赞某个绘画
     *
     * @param drawId 绘画ID
     * @param userId 用户ID
     * @return 是否已点赞
     */
    boolean checkIfLiked(Long drawId, Integer userId);

    /**
     * 添加点赞记录
     *
     * @param drawId 绘画ID
     * @param userId 用户ID
     * @return 操作结果
     */
    boolean addLike(Long drawId, Integer userId);

    /**
     * 移除点赞记录
     *
     * @param drawId 绘画ID
     * @param userId 用户ID
     * @return 操作结果
     */
    boolean removeLike(Long drawId, Integer userId);

    /**
     * 获取绘画的点赞数量
     *
     * @param drawId 绘画ID
     * @return 点赞数量
     */
    long getLikeCount(Long drawId);
}
