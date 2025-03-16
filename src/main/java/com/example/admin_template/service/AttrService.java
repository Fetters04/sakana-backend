package com.example.admin_template.service;

import com.example.admin_template.entity.Attr;

import java.util.List;

/**
 * @author Fetters
 */
public interface AttrService {
    // 获取三级分类关联数据
    List<Attr> getAttrInfoList(Integer category1Id, Integer category2Id, Integer category3Id);

    // 新增或修改属性及属性值
    Attr addOrUpdateAttr(Attr attr);

    // 删除属性及其对应属性值
    void deleteAttrById(Integer attrId);
}