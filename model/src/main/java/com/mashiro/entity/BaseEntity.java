package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonIgnore
    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+0")
    private Date createTime;

    @JsonIgnore
    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+0")
    private Date updateTime;

    @Schema(description = "逻辑删除")
    @TableField("is_deleted")
    @JsonIgnore // 忽略字段
    @TableLogic // TableLogic注解可以自动为查询操作增加`is_deleted=0`过滤条件，并将删除操作转为更新语句。
    private Byte isDeleted;

}