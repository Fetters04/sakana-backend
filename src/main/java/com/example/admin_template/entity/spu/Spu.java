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
@TableName("spu")
public class Spu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String spuName;
    private String description;
    private Integer category3Id;
    private Integer tmId;
    @TableField(exist = false)
    private List<SaleAttr> spuSaleAttrList;
    @TableField(exist = false)
    private List<SpuImage> spuImageList;
}