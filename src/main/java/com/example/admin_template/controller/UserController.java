package com.example.admin_template.controller;

import com.example.admin_template.entity.Result;
import com.example.admin_template.entity.vo.UserInfoVO;
import com.example.admin_template.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Fetters
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param requestBody
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> requestBody) {
        try {
            String username = requestBody.get("username");
            String password = requestBody.get("password");
            // 这里可能因为requestBody中没有"username"或"password"导致空指针异常
            return userService.login(username, password);
        } catch (Exception e) {
            // 可以返回更友好的错误信息给前端
            return Result.fail("登录失败，请稍后重试");
        }
    }

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    @GetMapping("/info")
    public Result<UserInfoVO> getUserInfo(@RequestHeader("token") String token) {
        return userService.getUserInfo(token);
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("token") String token) {
        return userService.logout(token);
    }
}