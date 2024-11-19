package com.mashiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashiro.entity.DrawLike;

import java.util.List;

public interface DrawLikeService extends IService<DrawLike> {
    DrawLike toggleLike(Long drawId, Integer userId);

    List<DrawLike> getLikesByDrawId(Long drawId);

    List<DrawLike> getLikesByUserId(Integer userId);
}
