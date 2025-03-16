package com.example.admin_template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin_template.entity.spu.SaleAttr;
import com.example.admin_template.entity.vo.HasSaleAttrVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Fetters
 */
public interface SaleAttrMapper extends BaseMapper<SaleAttr> {
    // 获取某SPU的销售属性
    List<SaleAttr> selectBySpuId(@Param("spuId") Integer spuId);

    // 获取全部销售属性
    @Select("SELECT DISTINCT base_sale_attr_id AS id, sale_attr_name AS name FROM sakana.sale_attr")
    List<HasSaleAttrVO> getAllSaleAttr();

    // 根据SPU的ID删除对应数据
    void deleteBySpuId(@Param("spuId") Integer spuId);
}




