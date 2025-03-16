package com.example.admin_template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin_template.entity.spu.SaleAttr;
import com.example.admin_template.entity.vo.HasSaleAttrVO;

import java.util.List;

/**
 * @author Fetters
 */
public interface SaleAttrService extends IService<SaleAttr> {
    // 获取某SPU的销售属性
    List<SaleAttr> getSpuSaleAttrList(Integer spuId);
    // 获取全部销售属性
    List<HasSaleAttrVO> getAllSaleAttr();
}
