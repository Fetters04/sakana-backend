package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.category.Category2;
import com.example.admin_template.mapper.Category2Mapper;
import com.example.admin_template.service.Category2Service;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fetters
 */
@Service
public class Category2ServiceImpl extends ServiceImpl<Category2Mapper, Category2>
        implements Category2Service {

    @Override
    public List<Category2> getByCategory1Id(Long category1Id) {
        return lambdaQuery().eq(Category2::getCategory1Id, category1Id).list();
    }
}




