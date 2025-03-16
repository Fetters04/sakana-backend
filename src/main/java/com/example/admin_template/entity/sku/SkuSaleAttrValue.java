package com.example.admin_template.entity.sku;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Fetters
 */
@TableName(value = "sku_sale_attr_value")
@Data
public class SkuSaleAttrValue {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer skuId;
    private Integer saleAttrId;
    private Integer saleAttrValueId;
    private Date createTime;
    private Date updateTime;
}