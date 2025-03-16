package com.example.admin_template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin_template.entity.spu.SpuImage;
import org.apache.ibatis.annotations.Param;


/**
 * @author Fetters
 */
public interface SpuImageMapper extends BaseMapper<SpuImage> {
    // 根据SPU的ID删除对应数据
    void deleteBySpuId(@Param("spuId") Integer spuId);
}




