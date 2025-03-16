package com.example.admin_template.entity.dto;

import lombok.Data;

@Data
public class PermissionDTO {
    private Integer id;
    private Integer pid;
    private String name;
    private String code;
    private Integer level;
}