package com.example.admin_template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author Fetters
 */
@Data
@TableName(value = "trademark")
public class Trademark {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date createTime;
    private Date updateTime;
    private String tmName;
    private String logoUrl;
}