package com.example.admin_template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author Fetters
 */
@Data
@TableName("attr")
public class Attr {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String attrName;
    private Integer categoryId;
    private Integer categoryLevel;
    @TableField(exist = false)
    private List<AttrValue> attrValueList;
}
