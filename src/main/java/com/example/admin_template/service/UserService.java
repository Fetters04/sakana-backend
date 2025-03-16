package com.example.admin_template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin_template.entity.Result;
import com.example.admin_template.entity.User;
import com.example.admin_template.entity.vo.UserInfoVO;

/**
 * @author Fetters
 */
public interface UserService extends IService<User> {
    Result<String> login(String username, String password);

    Result<UserInfoVO> getUserInfo(String token);

    Result<String> logout(String token);
}
