package com.example.admin_template.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin_template.entity.Result;
import com.example.admin_template.entity.spu.SaleAttr;
import com.example.admin_template.entity.spu.Spu;
import com.example.admin_template.entity.spu.SpuImage;
import com.example.admin_template.entity.vo.HasSaleAttrVO;
import com.example.admin_template.service.SaleAttrService;
import com.example.admin_template.service.SpuService;
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
public class SpuController {
    @Resource
    private SpuService spuService;
    @Resource
    private SaleAttrService saleAttrService;

    // 获取Spu分页数据
    @GetMapping("/{currentPage}/{pageSize}")
    public Result<Map<String, Object>> getHasSpu(@PathVariable int currentPage, @PathVariable int pageSize, @RequestParam int category3Id) {
        IPage<Spu> spuPage = spuService.getSpuList(currentPage, pageSize, category3Id);

        Map<String, Object> data = new HashMap<>();
        data.put("records", spuPage.getRecords());
        data.put("total", spuPage.getTotal());
        data.put("size", spuPage.getSize());
        data.put("current", spuPage.getCurrent());
        data.put("pages", spuPage.getPages());
        data.put("searchCount", true);

        return Result.success(data);
    }

    // 获取某个Spu的商品图片数据
    @GetMapping("/spuImageList/{spuId}")
    public Result<List<SpuImage>> reqSpuImageList(@PathVariable Integer spuId) {
        List<SpuImage> spuImageList = spuService.getSpuImageList(spuId);
        return Result.success(spuImageList);
    }

    // 获取某SPU的销售属性
    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result<List<SaleAttr>> reqSpuHasSaleAttr(@PathVariable Integer spuId) {
        List<SaleAttr> saleAttrList = saleAttrService.getSpuSaleAttrList(spuId);
        return Result.success(saleAttrList);
    }

    // 获取全部销售属性
    @GetMapping("/baseSaleAttrList")
    public Result<List<HasSaleAttrVO>> reqAllSaleAttr() {
        List<HasSaleAttrVO> allSaleAttr = saleAttrService.getAllSaleAttr();
        return Result.success(allSaleAttr);
    }

    // 新增一个 SPU
    @PostMapping("/saveSpuInfo")
    public Result<Void> saveSpuInfo(@RequestBody Spu spu) {
        spuService.saveSpu(spu);
        return Result.success(null);
    }

    // 修改已有的 SPU
    @PostMapping("/updateSpuInfo")
    public Result<Void> updateSpuInfo(@RequestBody Spu spu) {
        spuService.updateSpu(spu);
        return Result.success(null);
    }

    // 删除已有的 SPU
    @DeleteMapping("/deleteSpu/{spuId}")
    public Result<Void> deleteSpu(@PathVariable Integer spuId) {
        spuService.deleteSpu(spuId);
        return Result.success(null);
    }
}