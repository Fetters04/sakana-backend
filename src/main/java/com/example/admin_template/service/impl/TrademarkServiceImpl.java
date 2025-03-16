package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.Trademark;
import com.example.admin_template.service.TrademarkService;
import com.example.admin_template.mapper.TrademarkMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fetters
 */
@Service
public class TrademarkServiceImpl extends ServiceImpl<TrademarkMapper, Trademark>
        implements TrademarkService {
    @Resource
    private TrademarkMapper trademarkMapper;

    @Override
    public List<Trademark> getAllTrademarks() {
        return baseMapper.selectList(null);
    }

    @Override
    public Page<Trademark> getTrademarkPage(int currentPage, int pageSize) {
        Page<Trademark> page = new Page<>(currentPage, pageSize);
        return baseMapper.selectPage(page, null);
    }

    @Override
    public void addTrademark(Trademark trademark) {
        trademarkMapper.insert(trademark);
    }

    @Override
    public void updateTrademark(Trademark trademark) {
        trademarkMapper.updateById(trademark);
    }

    @Override
    public Boolean deleteTrademark(int id) {
        int rows = trademarkMapper.deleteById(id);
        return rows > 0;
    }
}




