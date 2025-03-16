package com.example.admin_template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin_template.entity.category.Category3;

import java.util.List;

/**
* @author Fetters
*/
public interface Category3Service extends IService<Category3> {
    // 根据二级分类ID查询三级分类
    List<Category3> getByCategory2Id(Long category2Id);
}
