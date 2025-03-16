package com.example.admin_template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Fetters
 */
@Data
@TableName("attr_value")
public class AttrValue {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String valueName;
    private Integer attrId;
}