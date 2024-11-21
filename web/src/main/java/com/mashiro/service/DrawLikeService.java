package com.mashiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashiro.entity.DrawLike;

public interface DrawLikeService extends IService<DrawLike> {

    /**
     * 获取绘画的点赞数量
     *
     * @param drawId 绘画ID
     * @return 点赞数量
     */
    long getLikeCount(Long drawId);

    /**
     * 点赞/取消点赞
     * @param drawId
     * @param userId
     * @return
     */
    boolean toggleLike(Long drawId, Integer userId);
}
