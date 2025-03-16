package com.example.admin_template.entity.sku;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Fetters
 */
@Data
@TableName(value = "sku_attr_value")
public class SkuAttrValue {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer skuId;
    private Integer attrId;
    private Integer valueId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}