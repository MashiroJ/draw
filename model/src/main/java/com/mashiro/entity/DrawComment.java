package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("draw_comment")
public class DrawComment extends BaseEntity{

    private Long drawId;
    
    private Integer userId;
    
    private String content;
    
    private Long parentId;
    
    private Integer replyUserId;
    
    private Integer likeCount;
} 