package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.sku.Sku;
import com.example.admin_template.entity.spu.SaleAttr;
import com.example.admin_template.entity.spu.SaleAttrValue;
import com.example.admin_template.entity.spu.Spu;
import com.example.admin_template.entity.spu.SpuImage;
import com.example.admin_template.mapper.*;
import com.example.admin_template.service.SpuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fetters
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu>
        implements SpuService {
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SpuImageMapper spuImageMapper;
    @Resource
    private SaleAttrMapper saleAttrMapper;
    @Resource
    private SaleAttrValueMapper saleAttrValueMapper;
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private SkuAttrValueMapper skuAttrValueMapper;
    @Resource
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    // 获取已有的SPU数据
    @Override
    public IPage<Spu> getSpuList(int currentPage, int pageSize, int category3Id) {
        Page<Spu> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Spu> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", category3Id);
        return spuMapper.selectPage(page, wrapper);
    }

    // 获取某个Spu的商品图片数据
    @Override
    public List<SpuImage> getSpuImageList(Integer spuId) {
        QueryWrapper<SpuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spu_id", spuId);
        return spuImageMapper.selectList(queryWrapper);
    }

    // 保存 SPU 信息
    @Override
    public void saveSpu(Spu spu) {
        // 保存 SPU 基本信息
        spuMapper.insert(spu);
        Integer spuId = spu.getId();

        // 保存 SPU 图片信息
        List<SpuImage> spuImageList = spu.getSpuImageList();
        if (spuImageList != null && !spuImageList.isEmpty()) {
            for (SpuImage spuImage : spuImageList) {
                spuImage.setSpuId(spuId);
                spuImageMapper.insert(spuImage);
            }
        }

        // 保存 SPU 销售属性信息
        List<SaleAttr> spuSaleAttrList = spu.getSpuSaleAttrList();
        if (spuSaleAttrList != null && !spuSaleAttrList.isEmpty()) {
            for (SaleAttr saleAttr : spuSaleAttrList) {
                saleAttr.setSpuId(spuId);
                saleAttrMapper.insert(saleAttr);
                // 保存销售属性值信息
                List<SaleAttrValue> spuSaleAttrValueList = saleAttr.getSpuSaleAttrValueList();
                if (spuSaleAttrValueList != null && !spuSaleAttrValueList.isEmpty()) {
                    for (SaleAttrValue saleAttrValue : spuSaleAttrValueList) {
                        saleAttrValue.setSpuId(spuId);
                        saleAttrValue.setBaseSaleAttrId(saleAttr.getBaseSaleAttrId());
                        // 设置 sale_attr_name 的值
                        saleAttrValue.setSaleAttrName(saleAttr.getSaleAttrName());
                        saleAttrValueMapper.insert(saleAttrValue);
                    }
                }
            }
        }
    }

    // 更新 SPU 信息
    @Override
    public void updateSpu(Spu spu) {
        Integer spuId = spu.getId();
        // 更新 SPU 基本信息
        spuMapper.updateById(spu);
        // 删除原有的 SPU 图片信息
        spuImageMapper.deleteBySpuId(spuId);
        // 保存新的 SPU 图片信息
        List<SpuImage> spuImageList = spu.getSpuImageList();
        if (spuImageList != null && !spuImageList.isEmpty()) {
            for (SpuImage spuImage : spuImageList) {
                spuImage.setSpuId(spuId);
                spuImageMapper.insert(spuImage);
            }
        }

        // 先删除 sale_attr_value 表中关联的数据
        saleAttrValueMapper.deleteBySpuId(spuId);
        // 再删除原有的 SPU 销售属性信息
        saleAttrMapper.deleteBySpuId(spuId);

        // 保存新的 SPU 销售属性信息
        List<SaleAttr> spuSaleAttrList = spu.getSpuSaleAttrList();
        if (spuSaleAttrList != null && !spuSaleAttrList.isEmpty()) {
            for (SaleAttr saleAttr : spuSaleAttrList) {
                saleAttr.setSpuId(spuId);
                saleAttrMapper.insert(saleAttr);
                // 保存新的销售属性值信息
                List<SaleAttrValue> spuSaleAttrValueList = saleAttr.getSpuSaleAttrValueList();
                if (spuSaleAttrValueList != null && !spuSaleAttrValueList.isEmpty()) {
                    for (SaleAttrValue saleAttrValue : spuSaleAttrValueList) {
                        saleAttrValue.setSpuId(spuId);
                        saleAttrValue.setBaseSaleAttrId(saleAttr.getBaseSaleAttrId());
                        // 设置 sale_attr_name 的值
                        saleAttrValue.setSaleAttrName(saleAttr.getSaleAttrName());
                        saleAttrValueMapper.insert(saleAttrValue);
                    }
                }
            }
        }
    }

    // 删除 SPU 及其关联数据
    @Override
    @Transactional
    public void deleteSpu(Integer spuId) {
        // 1. 删除关联的 SKU 数据
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.eq("spu_id", spuId);
        List<Sku> skuList = skuMapper.selectList(skuQueryWrapper);
        if (!skuList.isEmpty()) {
            List<Integer> skuIds = skuList.stream().map(Sku::getId).toList();
            // 删除 SKU 平台属性值
            skuAttrValueMapper.deleteBySkuIds(skuIds);
            // 删除 SKU 销售属性值
            skuSaleAttrValueMapper.deleteBySkuIds(skuIds);
            // 删除 SKU 记录
            skuMapper.deleteBySpuId(spuId);
        }

        // 2. 删除 SPU 销售属性值
        saleAttrValueMapper.deleteBySpuId(spuId);
        // 3. 删除 SPU 销售属性
        saleAttrMapper.deleteBySpuId(spuId);
        // 4. 删除 SPU 图片
        spuImageMapper.deleteBySpuId(spuId);
        // 5. 删除 SPU 记录
        spuMapper.deleteById(spuId);
    }
}




