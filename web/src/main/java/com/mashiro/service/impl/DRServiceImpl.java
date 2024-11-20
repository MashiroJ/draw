package com.mashiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.DrawRecord;
import com.mashiro.mapper.DrawRecordMapper;
import com.mashiro.service.DRService;
import com.mashiro.vo.DrawRecordVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DRServiceImpl extends ServiceImpl<DrawRecordMapper, DrawRecord> implements DRService {

    @Resource
    private DrawRecordMapper drawRecordMapper; // 假设有这个Mapper

    @Override
    public List<DrawRecordVO> listAllDrawRecords() {
        // 从数据库查询所有绘图记录并转换为VO，过滤掉is_public为0的记录
        return drawRecordMapper.selectList(null).stream()
                .filter(drawRecord -> drawRecord.getIsPublic() != 0) // 过滤掉is_public为0的记录
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateDrawRecordVisibility(Long id, Boolean isPublic) {
        // 创建一个更新对象
        DrawRecord drawRecord = new DrawRecord();
        drawRecord.setId(id);
        drawRecord.setIsPublic(isPublic ? (byte) 1 : (byte) 0); // 将Boolean转换为tinyint

        // 更新数据库记录
        drawRecordMapper.updateById(drawRecord);
    }

    @Override
    public List<DrawRecordVO> listDrawRecordsSortedByLikes() {
        // 从数据库查询所有绘图记录并根据点赞数量排序，过滤掉is_public为0的记录
        return drawRecordMapper.selectList(null).stream()
                .filter(drawRecord -> drawRecord.getIsPublic() != 0) // 过滤掉is_public为0的记录
                .sorted(Comparator.comparingInt(DrawRecord::getLikeCount).reversed()) // 按点赞数量降序排序
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DrawRecordVO> listDrawRecordsSortedByLatest() {
        // 从数据库查询所有绘图记录并根据创建时间排序，过滤掉is_public为0的记录
        return drawRecordMapper.selectList(null).stream()
                .filter(drawRecord -> drawRecord.getIsPublic() != 0) // 过滤掉is_public为0的记录
                .sorted(Comparator.comparing(DrawRecord::getCreateTime).reversed()) // 按创建时间降序排序
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DrawRecordVO> listDrawRecordsByUserId(Integer userId) {
        // 从数据库查询该用户的所有绘图记录，过滤掉is_public为0的记录
        return drawRecordMapper.selectList(new QueryWrapper<DrawRecord>().eq("user_id", userId)).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private DrawRecordVO convertToVO(DrawRecord drawRecord) {
        DrawRecordVO vo = new DrawRecordVO();
        BeanUtils.copyProperties(drawRecord, vo);
        return vo;
    }
}