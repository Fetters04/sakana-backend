package com.example.admin_template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin_template.entity.sku.Sku;

/**
 * @author Fetters
 */
public interface SkuMapper extends BaseMapper<Sku> {
    // 根据 SPU 的 id 删除所关联的 SPU 数据
    void deleteBySpuId(Integer spuId);
}




