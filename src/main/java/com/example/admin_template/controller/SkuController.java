package com.example.admin_template.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin_template.entity.Result;
import com.example.admin_template.entity.sku.Sku;
import com.example.admin_template.entity.SkuInfoVO;
import com.example.admin_template.service.SkuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fetters
 */
@RestController
@RequestMapping("/product")
public class SkuController {
    @Resource
    private SkuService skuService;

    /**
     * 新增 SKU
     *
     * @param sku
     * @return
     */
    @PostMapping("/saveSkuInfo")
    public Result<Void> saveSkuInfo(@RequestBody Sku sku) {
        skuService.saveSku(sku);
        return Result.success(null);
    }

    /**
     * 根据 spuId 获取其下全部 SKU
     *
     * @param spuId
     * @return
     */
    @GetMapping("/findBySpuId/{spuId}")
    public Result<List<Sku>> getSkusBySpuId(@PathVariable String spuId) {
        List<Sku> skuList = skuService.getSkusBySpuId(spuId);
        return Result.success(skuList);
    }

    /**
     * 获取 sku 分页数据
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result<Map<String, Object>> list(@PathVariable int currentPage, @PathVariable int pageSize) {
        IPage<Sku> skuPage = skuService.getSkuList(currentPage, pageSize);

        Map<String, Object> data = new HashMap<>();
        data.put("records", skuPage.getRecords());
        data.put("total", skuPage.getTotal());
        data.put("size", skuPage.getSize());
        data.put("current", skuPage.getCurrent());
        data.put("pages", skuPage.getPages());
        data.put("searchCount", true);

        return Result.success(data);
    }

    /**
     * SKU商品上架
     *
     * @param skuId
     * @return
     */
    @GetMapping("/onSale/{skuId}")
    public Result<Void> onSale(@PathVariable int skuId) {
        skuService.onSale(skuId);
        return Result.success(null);
    }

    /**
     * SKU商品下架
     *
     * @param skuId
     * @return
     */
    @GetMapping("/cancelSale/{skuId}")
    public Result<Void> cancelSale(@PathVariable int skuId) {
        skuService.cancelSale(skuId);
        return Result.success(null);
    }

    /**
     * 根据 SKU 的 id 获取对应数据
     *
     * @param id
     * @return
     */
    @GetMapping("/getSkuInfo/{id}")
    public Result<SkuInfoVO> getSkuInfo(@PathVariable int id) {
        SkuInfoVO skuInfoVO = skuService.getSkuById(id);
        return Result.success(skuInfoVO);
    }

    /**
     * 删除 SKU
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteSku/{id}")
    public Result<Void> deleteSku(@PathVariable int id) {
        skuService.deleteSku(id);
        return Result.success(null);
    }

}
