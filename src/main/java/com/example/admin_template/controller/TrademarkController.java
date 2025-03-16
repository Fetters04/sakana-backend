package com.example.admin_template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin_template.entity.Result;
import com.example.admin_template.entity.Trademark;
import com.example.admin_template.service.OssService;
import com.example.admin_template.service.TrademarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Fetters
 */
@RestController
@RequestMapping("/product")
public class TrademarkController {
    @Resource
    private TrademarkService trademarkService;
    @Resource
    private OssService ossService;

    @GetMapping("/baseTrademark")
    public Result<List<Trademark>> getAllTrademarks() {
        List<Trademark> trademarks = trademarkService.getAllTrademarks();
        return Result.success(trademarks);
    }

    @GetMapping("/baseTrademark/{currentPage}/{pageSize}")
    public Result<Page<Trademark>> baseTrademark(@PathVariable int currentPage, @PathVariable int pageSize) {
        Page<Trademark> page = trademarkService.getTrademarkPage(currentPage, pageSize);
        System.out.println(page);
        return Result.success(page);
    }

    @PostMapping("/baseAddTrademark/save")
    public Result<Void> addTrademark(@RequestBody Trademark trademark) {
        trademarkService.addTrademark(trademark);
        return Result.success(null);
    }

    @PutMapping("/baseUpdateTrademark/update")
    public Result<Void> updateTrademark(@RequestBody Trademark trademark) {
        trademarkService.updateTrademark(trademark);
        return Result.success(null);
    }

    @PostMapping("/fileUpload")
    public Result<String> fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail("请选择要上传的文件");
        }

        String fileUrl = ossService.uploadFile(file);
        if (fileUrl != null) {
            return Result.success(fileUrl);
        } else {
            return Result.fail("文件上传失败");
        }
    }

    @DeleteMapping("/baseDeleteTrademark/remove/{id}")
    public Result<Void> deleteTrademark(@PathVariable int id) {
        boolean isDeleted = trademarkService.deleteTrademark(id);
        if (isDeleted) {
            return Result.success(null);
        } else {
            return Result.fail("删除失败，商标不存在或已被删除");
        }
    }
}
