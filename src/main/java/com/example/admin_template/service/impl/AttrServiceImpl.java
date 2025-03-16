package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.Attr;
import com.example.admin_template.entity.AttrValue;
import com.example.admin_template.mapper.AttrMapper;
import com.example.admin_template.mapper.AttrValueMapper;
import com.example.admin_template.service.AttrService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fetters
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr>
        implements AttrService {
    @Resource
    private AttrMapper attrMapper;
    @Resource
    private AttrValueMapper attrValueMapper;

    // 获取三级分类关联数据
    @Override
    public List<Attr> getAttrInfoList(Integer category1Id, Integer category2Id, Integer category3Id) {
        // 先根据三级分类 ID 查询属性
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("category_id", category3Id).eq("category_level", 3);
        List<Attr> attrList = attrMapper.selectList(attrQueryWrapper);

        // 为每个属性查询对应的属性值
        for (Attr attr : attrList) {
            QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
            attrValueQueryWrapper.eq("attr_id", attr.getId());
            List<AttrValue> attrValueList = attrValueMapper.selectList(attrValueQueryWrapper);
            attr.setAttrValueList(attrValueList);
        }

        return attrList;
    }

    // 新增或修改属性及属性值
    @Override
    @Transactional
    public Attr addOrUpdateAttr(Attr attr) {
        if (attr.getId() == null) {
            // 新增属性
            attrMapper.insert(attr);
            if (attr.getAttrValueList() != null && !attr.getAttrValueList().isEmpty()) {
                for (AttrValue attrValue : attr.getAttrValueList()) {
                    attrValue.setAttrId(attr.getId());
                    attrValueMapper.insert(attrValue);
                }
            }
        } else {
            // 修改属性
            attrMapper.updateById(attr);
            // 先删除原有的属性值
            QueryWrapper<AttrValue> wrapper = new QueryWrapper<>();
            wrapper.eq("attr_id", attr.getId());
            attrValueMapper.delete(wrapper);
            // 再插入新的属性值
            if (attr.getAttrValueList() != null && !attr.getAttrValueList().isEmpty()) {
                for (AttrValue attrValue : attr.getAttrValueList()) {
                    attrValue.setAttrId(attr.getId());
                    attrValueMapper.insert(attrValue);
                }
            }
        }
        return attr;
    }

    // 删除属性及其对应属性值
    @Override
    @Transactional
    public void deleteAttrById(Integer attrId) {
        // 先删除该属性对应的属性值
        QueryWrapper<AttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id", attrId);
        attrValueMapper.delete(wrapper);
        // 再删除该属性
        attrMapper.deleteById(attrId);
    }
}
