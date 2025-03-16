package com.example.admin_template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin_template.entity.acl.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Fetters
 */
public interface AclUserService extends IService<AclUser> {
    /**
     * 获取用户分页数据，并根据用户名进行模糊查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<AclUser> getUserInfoPage(int currentPage, int pageSize, String username);

    /**
     * 添加用户信息
     *
     * @param aclUser
     * @return
     */
    boolean saveUser(AclUser aclUser);

    /**
     * 修改用户信息
     *
     * @param aclUser
     * @return
     */
    boolean updateUser(AclUser aclUser);

    /**
     * 根据 id 删除单个用户
     *
     * @param userId
     */
    void deleteById(int userId);

    /**
     * 根据多个 id 批量删除用户
     *
     * @param userIds
     */
    void deleteByIds(List<Integer> userIds);
}
