package com.example.admin_template.mapper;

import com.example.admin_template.entity.sku.SkuSaleAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Fetters
 */
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {
    // 根据多个 sku 的 id 删除数据
    void deleteBySkuIds(List<Integer> skuIds);
}




