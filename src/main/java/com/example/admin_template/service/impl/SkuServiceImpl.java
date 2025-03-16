package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.*;
import com.example.admin_template.entity.SkuInfoVO;
import com.example.admin_template.entity.sku.Sku;
import com.example.admin_template.entity.sku.SkuAttrValue;
import com.example.admin_template.entity.sku.SkuSaleAttrValue;
import com.example.admin_template.entity.spu.SaleAttr;
import com.example.admin_template.entity.spu.SaleAttrValue;
import com.example.admin_template.entity.vo.SkuInfoAttrValueVO;
import com.example.admin_template.entity.vo.SkuInfoSaleAttrValueVO;
import com.example.admin_template.mapper.*;
import com.example.admin_template.service.SkuService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fetters
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku>
        implements SkuService {
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private SkuAttrValueMapper skuAttrValueMapper;
    @Resource
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Resource
    private AttrMapper attrMapper;
    @Resource
    private AttrValueMapper attrValueMapper;
    @Autowired
    private SaleAttrMapper saleAttrMapper;
    @Autowired
    private SaleAttrValueMapper saleAttrValueMapper;

    /**
     * 新增 SKU 数据
     *
     * @param sku
     */
    @Override
    @Transactional
    public void saveSku(Sku sku) {
        // 保存 SKU 基本信息
        skuMapper.insert(sku);
        Integer skuId = sku.getId();

        // 保存 SKU 平台属性信息
        List<SkuAttrValue> skuAttrValueList = sku.getSkuAttrValueList();
        if (skuAttrValueList != null && !skuAttrValueList.isEmpty()) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuId);
                skuAttrValueMapper.insert(skuAttrValue);
            }
        }

        // 保存 SKU 销售属性信息
        List<SkuSaleAttrValue> skuSaleAttrValueList = sku.getSkuSaleAttrValueList();
        if (skuSaleAttrValueList != null && !skuSaleAttrValueList.isEmpty()) {
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(skuId);
                skuSaleAttrValueMapper.insert(skuSaleAttrValue);
            }
        }
    }

    /**
     * 根据 spuId 获取其下全部 SKU
     *
     * @param spuId
     * @return
     */
    @Override
    public List<Sku> getSkusBySpuId(String spuId) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Sku> queryWrapper = new QueryWrapper<>();
        // 设置查询条件：spu_id 等于传入的 spuId
        queryWrapper.eq("spu_id", spuId);
        // 调用 selectList 方法执行查询
        List<Sku> skuList = skuMapper.selectList(queryWrapper);

        // 为每个 SKU 查询对应的 skuAttrValueList 和 skuSaleAttrValueList
        for (Sku sku : skuList) {
            Integer skuId = sku.getId();

            // 查询 skuAttrValueList
            QueryWrapper<SkuAttrValue> attrQueryWrapper = new QueryWrapper<>();
            attrQueryWrapper.eq("sku_id", skuId);
            List<SkuAttrValue> skuAttrValueList = skuAttrValueMapper.selectList(attrQueryWrapper);
            sku.setSkuAttrValueList(skuAttrValueList);

            // 查询 skuSaleAttrValueList
            QueryWrapper<SkuSaleAttrValue> saleAttrQueryWrapper = new QueryWrapper<>();
            saleAttrQueryWrapper.eq("sku_id", skuId);
            List<SkuSaleAttrValue> skuSaleAttrValueList = skuSaleAttrValueMapper.selectList(saleAttrQueryWrapper);
            sku.setSkuSaleAttrValueList(skuSaleAttrValueList);
        }

        return skuList;
    }

    /**
     * 获取 SKU 分页数据
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Sku> getSkuList(int currentPage, int pageSize) {
        Page<Sku> page = new Page<>(currentPage, pageSize);
        return skuMapper.selectPage(page, null);
    }

    /**
     * 上架 SKU
     *
     * @param skuId
     */
    @Override
    public void onSale(int skuId) {
        UpdateWrapper<Sku> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", skuId);
        // 设置 is_sale 字段为 1
        updateWrapper.set("is_sale", 1);
        skuMapper.update(null, updateWrapper);
    }

    /**
     * 下架 SKU
     *
     * @param skuId
     */
    @Override
    public void cancelSale(int skuId) {
        UpdateWrapper<Sku> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", skuId);
        // 设置 is_sale 字段为 0
        updateWrapper.set("is_sale", 0);
        skuMapper.update(null, updateWrapper);
    }

    /**
     * 根据 id 获取 SKU 数据
     *
     * @param id
     * @return
     */
    @Override
    public SkuInfoVO getSkuById(int id) {
        QueryWrapper<Sku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Sku sku = skuMapper.selectOne(queryWrapper);

        // 封装VO对象
        SkuInfoVO skuInfoVO = new SkuInfoVO();
        BeanUtils.copyProperties(sku, skuInfoVO);

        // 获取 sku 平台属性数据
        QueryWrapper<SkuAttrValue> skuAttrValueQueryWrapper = new QueryWrapper<>();
        skuAttrValueQueryWrapper.eq("sku_id", sku.getId());
        List<SkuAttrValue> skuAttrValueList = skuAttrValueMapper.selectList(skuAttrValueQueryWrapper);
        // SkuInfoVO的平台属性VO
        List<SkuInfoAttrValueVO> skuInfoAttrValueVOList = new ArrayList<>();

        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            // 创建平台属性VO对象
            SkuInfoAttrValueVO skuInfoAttrValueVO = new SkuInfoAttrValueVO();
            BeanUtils.copyProperties(skuAttrValue, skuInfoAttrValueVO);

            // 拿到平台属性和其值的 id
            int attrId = skuInfoAttrValueVO.getAttrId();
            int valueId = skuInfoAttrValueVO.getValueId();

            // 查出对应的 Name 封装到 VO
            QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
            attrQueryWrapper.eq("id", attrId);
            Attr attr = attrMapper.selectOne(attrQueryWrapper);
            skuInfoAttrValueVO.setAttrName(attr.getAttrName());

            QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
            attrValueQueryWrapper.eq("id", valueId);
            AttrValue attrValue = attrValueMapper.selectOne(attrValueQueryWrapper);
            skuInfoAttrValueVO.setAttrValueName(attrValue.getValueName());

            // 加入到集合中
            skuInfoAttrValueVOList.add(skuInfoAttrValueVO);
        }
        // 封装到 skuInfoAttrValueVO
        skuInfoVO.setSkuInfoAttrValueVO(skuInfoAttrValueVOList);


        // 获取 sku 销售属性数据
        QueryWrapper<SkuSaleAttrValue> skuSaleAttrValueQueryWrapper = new QueryWrapper<>();
        skuSaleAttrValueQueryWrapper.eq("sku_id", sku.getId());
        List<SkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueMapper.selectList(skuSaleAttrValueQueryWrapper);
        // SkuInfoVO的销售属性VO
        List<SkuInfoSaleAttrValueVO> skuInfoSaleAttrValueVOList = new ArrayList<>();

        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValues) {
            // 创建销售属性VO对象
            SkuInfoSaleAttrValueVO skuInfoSaleAttrValueVO = new SkuInfoSaleAttrValueVO();
            BeanUtils.copyProperties(skuSaleAttrValue, skuInfoSaleAttrValueVO);

            // 拿到销售属性和其值的 id
            int saleAttrId = skuInfoSaleAttrValueVO.getSaleAttrId();
            int saleAttrValueId = skuInfoSaleAttrValueVO.getSaleAttrValueId();

            // 查出对应的 Name 封装到 VO
            QueryWrapper<SaleAttr> saleAttrQueryWrapper = new QueryWrapper<>();
            saleAttrQueryWrapper.eq("id", saleAttrId);
            SaleAttr saleAttr = saleAttrMapper.selectOne(saleAttrQueryWrapper);
            skuInfoSaleAttrValueVO.setSaleAttrName(saleAttr.getSaleAttrName());

            QueryWrapper<SaleAttrValue> saleAttrValueQueryWrapper = new QueryWrapper<>();
            saleAttrValueQueryWrapper.eq("id", saleAttrValueId);
            SaleAttrValue saleAttrValue = saleAttrValueMapper.selectOne(saleAttrValueQueryWrapper);
            skuInfoSaleAttrValueVO.setSaleAttrValueName(saleAttrValue.getSaleAttrValueName());

            // 加入到集合中
            skuInfoSaleAttrValueVOList.add(skuInfoSaleAttrValueVO);
        }
        // 封装到 skuInfoSaleAttrValueVOList
        skuInfoVO.setSkuInfoSaleAttrValueVO(skuInfoSaleAttrValueVOList);

        return skuInfoVO;
    }

    /**
     * 根据 id 删除 SKU
     *
     * @param id
     */
    @Override
    public void deleteSku(int id) {
        skuMapper.deleteById(id);
    }
}




