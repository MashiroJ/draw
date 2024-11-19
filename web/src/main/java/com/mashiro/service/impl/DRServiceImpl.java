package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.DrawRecord;
import com.mashiro.mapper.DrawRecordMapper;
import com.mashiro.service.DRService;
import org.springframework.stereotype.Service;

@Service
public class DRServiceImpl extends ServiceImpl<DrawRecordMapper, DrawRecord> implements DRService {

}
