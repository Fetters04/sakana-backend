package com.example.admin_template.entity.acl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author Fetters
 * @TableName acl_user
 */
@Data
@TableName(value ="acl_user")
public class AclUser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String phone;
    private String roleName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}