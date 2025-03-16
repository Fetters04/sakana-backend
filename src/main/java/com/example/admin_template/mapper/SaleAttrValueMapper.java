package com.example.admin_template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin_template.entity.spu.SaleAttrValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Fetters
 */
public interface SaleAttrValueMapper extends BaseMapper<SaleAttrValue> {
    // 根据 spuId 和 baseSaleAttrId 查询 SaleAttrValue 列表
    List<SaleAttrValue> selectBySpuIdAndBaseSaleAttrId(@Param("spuId") Integer spuId,
                                                       @Param("baseSaleAttrId") Integer baseSaleAttrId);
    // 根据 spuId 删除数据
    void deleteBySpuId(@Param("spuId") Integer spuId);
}




