package com.example.admin_template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;


import java.util.List;

/**
 * @author Fetters
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String avatar;
    private String username;
    private String password;
    @TableField(exist = false)
    private List<String> roles;
    @TableField(exist = false)
    private List<String> buttons;
    @TableField(exist = false)
    private List<String> routes;
    private String token;
}
