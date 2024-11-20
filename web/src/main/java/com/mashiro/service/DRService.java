package com.mashiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashiro.entity.DrawRecord;
import com.mashiro.vo.DrawRecordVO;

import java.util.List;

public interface DRService extends IService<DrawRecord> {

    List<DrawRecordVO> listAllDrawRecords();

    void updateDrawRecordVisibility(Long id, Boolean isPublic);

    List<DrawRecordVO> listDrawRecordsSortedByLikes();

    List<DrawRecordVO> listDrawRecordsSortedByLatest();

    List<DrawRecordVO> listDrawRecordsByUserId(Integer userId);
}
