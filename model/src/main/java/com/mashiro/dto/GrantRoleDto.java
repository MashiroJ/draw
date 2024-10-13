package com.mashiro.dto;

import lombok.Data;

import java.util.List;

@Data
public class GrantRoleDto {

    private Long userId;				// 用户的id
    private List<Long> roleIdList;		// 角色id

}