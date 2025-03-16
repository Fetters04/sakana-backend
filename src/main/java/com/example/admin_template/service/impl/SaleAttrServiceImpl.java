package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.admin_template.entity.spu.SaleAttr;
import com.example.admin_template.entity.spu.SaleAttrValue;
import com.example.admin_template.entity.vo.HasSaleAttrVO;
import com.example.admin_template.mapper.SaleAttrMapper;
import com.example.admin_template.mapper.SaleAttrValueMapper;
import com.example.admin_template.service.SaleAttrService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fetters
 */
@Service
public class SaleAttrServiceImpl extends ServiceImpl<SaleAttrMapper, SaleAttr>
        implements SaleAttrService {
    @Resource
    private SaleAttrMapper saleAttrMapper;
    @Resource
    private SaleAttrValueMapper saleAttrValueMapper;

    // 获取某SPU的销售属性
    @Override
    public List<SaleAttr> getSpuSaleAttrList(Integer spuId) {
        // 查询该 SPU 下的所有销售属性
        List<SaleAttr> saleAttrList = saleAttrMapper.selectBySpuId(spuId);
        // 为每个销售属性查询对应的销售属性值
        for (SaleAttr saleAttr : saleAttrList) {
            List<SaleAttrValue> saleAttrValueList = saleAttrValueMapper.selectBySpuIdAndBaseSaleAttrId(spuId, saleAttr.getBaseSaleAttrId());
            saleAttr.setSpuSaleAttrValueList(saleAttrValueList);
        }
        return saleAttrList;
    }

    // 获取全部销售属性
    @Override
    public List<HasSaleAttrVO> getAllSaleAttr() {
        return saleAttrMapper.getAllSaleAttr();
    }
}




