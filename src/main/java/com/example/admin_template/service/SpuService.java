package com.example.admin_template.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin_template.entity.spu.Spu;
import com.example.admin_template.entity.spu.SpuImage;

import java.util.List;


/**
 * @author Fetters
 */
public interface SpuService extends IService<Spu> {
    // 获取已有的SPU数据
    IPage<Spu> getSpuList(int currentPage, int pageSize, int category3Id);

    // 获取某个Spu的商品图片数据
    List<SpuImage> getSpuImageList(Integer spuId);

    // 保存 SPU 信息
    void saveSpu(Spu spu);

    // 更新 SPU 信息
    void updateSpu(Spu spu);

    // 删除 SPU 数据
    void deleteSpu(Integer spuId);
}
