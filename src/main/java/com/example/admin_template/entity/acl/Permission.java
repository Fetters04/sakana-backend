package com.example.admin_template.entity.acl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Fetters
 */
@Data
@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String name;
    private String code;
    private Integer type;
    private Integer level;
    private Boolean select;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    @TableField(exist = false)
    private List<Permission> children;
}