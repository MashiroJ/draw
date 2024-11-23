package com.mashiro.service;

import com.mashiro.result.Result;

public interface PointsService {
    Result<String> signIn(long userId);

    Result<Integer> getPoints(long userId);

    Result<String> deductPoints(long userId, int points);
}
