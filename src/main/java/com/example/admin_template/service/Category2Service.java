package com.example.admin_template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin_template.entity.category.Category2;

import java.util.List;

/**
* @author Fetters
*/
public interface Category2Service extends IService<Category2> {
    // 根据一级分类ID查询二级分类
    List<Category2> getByCategory1Id(Long category1Id);
}
