package com.example.admin_template.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Fetters
 */
@Data
public class SkuInfoAttrValueVO {
    private Integer id;
    private Integer skuId;
    private Integer attrId;
    private Integer valueId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    // 封装到 skuInfoAttrValueVO 用于数据展示
    private String attrName;
    private String attrValueName;
}
