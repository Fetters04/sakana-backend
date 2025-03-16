package com.example.admin_template.entity.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Fetters
 */
@Data
@TableName("category3")
public class Category3 {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long category2Id;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("update_time")
    private LocalDateTime updateTime;
}