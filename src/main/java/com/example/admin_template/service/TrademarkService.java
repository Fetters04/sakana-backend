package com.example.admin_template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin_template.entity.Trademark;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Fetters
*/
public interface TrademarkService extends IService<Trademark> {
    // 获取全部品牌数据
    List<Trademark> getAllTrademarks();

    // 获取品牌分页数据
    Page<Trademark> getTrademarkPage(int currentPage, int pageSize);

    // 添加品牌
    void addTrademark(Trademark trademark);

    // 修改品牌
    void updateTrademark(Trademark trademark);

    // 删除品牌
    Boolean deleteTrademark(int id);
}
