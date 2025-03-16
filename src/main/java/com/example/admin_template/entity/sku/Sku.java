package com.example.admin_template.entity.sku;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Fetters
 */
@Data
@TableName(value = "sku")
public class Sku {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer category3Id;
    private Integer spuId;
    private Integer tmId;
    private String skuName;
    private BigDecimal price;
    private BigDecimal weight;
    private String skuDesc;
    private String skuDefaultImg;
    private Integer isSale;
    @TableField(exist = false)
    private List<SkuAttrValue> skuAttrValueList;
    @TableField(exist = false)
    private List<SkuSaleAttrValue> skuSaleAttrValueList;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}