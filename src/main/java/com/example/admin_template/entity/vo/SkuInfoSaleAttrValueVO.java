package com.example.admin_template.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Fetters
 */
@Data
public class SkuInfoSaleAttrValueVO {
    private Integer id;
    private Integer skuId;
    private Integer saleAttrId;
    private Integer saleAttrValueId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    // 封装到 skuInfoSaleAttrValueVO 用于数据展示
    private String saleAttrName;
    private String saleAttrValueName;
}
