package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.category.Category3;
import com.example.admin_template.mapper.Category3Mapper;
import com.example.admin_template.service.Category3Service;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Fetters
*/
@Service
public class Category3ServiceImpl extends ServiceImpl<Category3Mapper, Category3>
        implements Category3Service {

    @Override
    public List<Category3> getByCategory2Id(Long category2Id) {
        return lambdaQuery().eq(Category3::getCategory2Id, category2Id).list();
    }
}




