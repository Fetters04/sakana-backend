package com.example.admin_template.entity.spu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Fetters
 */
@Data
@TableName(value = "sale_attr_value")
public class SaleAttrValue {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer spuId;
    private Integer baseSaleAttrId;
    private String saleAttrValueName;
    private String saleAttrName;
    private Integer isChecked;
}