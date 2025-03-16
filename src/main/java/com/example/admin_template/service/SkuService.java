package com.example.admin_template.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin_template.entity.sku.Sku;
import com.example.admin_template.entity.SkuInfoVO;

import java.util.List;

/**
 * @author Fetters
 */
public interface SkuService extends IService<Sku> {
    // 新增 SKU 数据
    void saveSku(Sku sku);

    // 根据 spuId 获取其下全部 SKU
    List<Sku> getSkusBySpuId(String spuId);

    // 获取 SKU 分页数据
    IPage<Sku> getSkuList(int currentPage, int pageSize);

    // 上架
    void onSale(int skuId);

    // 下架
    void cancelSale(int skuId);

    // 根据 id 获取 SKU 数据
    SkuInfoVO getSkuById(int id);

    // 根据 id 删除 SKU
    void deleteSku(int id);
}
