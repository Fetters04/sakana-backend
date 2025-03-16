package com.example.admin_template.controller;

import com.example.admin_template.entity.*;
import com.example.admin_template.entity.category.Category1;
import com.example.admin_template.entity.category.Category2;
import com.example.admin_template.entity.category.Category3;
import com.example.admin_template.service.AttrService;
import com.example.admin_template.service.Category1Service;
import com.example.admin_template.service.Category2Service;
import com.example.admin_template.service.Category3Service;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fetters
 */
@RestController
@RequestMapping("/product")
public class CategoryController {
    @Resource
    private Category1Service category1Service;
    @Resource
    private Category2Service category2Service;
    @Resource
    private Category3Service category3Service;
    @Resource
    private AttrService attrService;

    // 获取一级分类
    @GetMapping("/getCategory1")
    public Result<List<Category1>> getCategory1() {
        List<Category1> category1List = category1Service.list();
        return Result.success(category1List);
    }

    // 获取二级分类
    @GetMapping("/getCategory2/{category1Id}")
    public Result<List<Category2>> getCategory2(@PathVariable Long category1Id) {
        List<Category2> category2List = category2Service.getByCategory1Id(category1Id);
        return Result.success(category2List);
    }

    // 获取三级分类
    @GetMapping("/getCategory3/{category2Id}")
    public Result<List<Category3>> getCategory3(@PathVariable Long category2Id) {
        List<Category3> category3List = category3Service.getByCategory2Id(category2Id);
        return Result.success(category3List);
    }

    // 获取已有属性和属性值
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result<List<Attr>> getAttrInfoList(@PathVariable Integer category1Id, @PathVariable Integer category2Id, @PathVariable Integer category3Id) {
        List<Attr> attrInfoList = attrService.getAttrInfoList(category1Id, category2Id, category3Id);
        return Result.success(attrInfoList);
    }

    // 新增或修改属性及属性值的通用接口
    @PostMapping("/saveAttrInfo")
    public Result<Attr> addOrUpdateAttr(@RequestBody Attr attr) {
        Attr attrObj = attrService.addOrUpdateAttr(attr);
        return Result.success(attrObj);
    }

    // 删除属性的接口
    @DeleteMapping("/deleteAttr/{attrId}")
    public Result<Void> deleteAttrById(@PathVariable Integer attrId) {
        attrService.deleteAttrById(attrId);
        return Result.success(null);
    }
}