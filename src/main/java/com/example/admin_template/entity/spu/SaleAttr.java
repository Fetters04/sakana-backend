package com.example.admin_template.entity.spu;

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
@TableName(value = "sale_attr")
public class SaleAttr {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer spuId;
    private Integer baseSaleAttrId;
    private String saleAttrName;
    @TableField(exist = false)
    private List<SaleAttrValue> spuSaleAttrValueList;
}