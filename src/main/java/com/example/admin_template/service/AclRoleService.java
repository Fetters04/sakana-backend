package com.example.admin_template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin_template.entity.acl.AclRole;
import com.example.admin_template.entity.dto.RoleDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Fetters
 * @description 针对表【acl_roles】的数据库操作Service
 * @createDate 2025-02-21 21:17:39
 */
public interface AclRoleService extends IService<AclRole> {
    /**
     * 获取所有角色和用户的角色
     *
     * @param userId
     * @return
     */
    Map<String, List<AclRole>> getAllRoleAndUserRole(int userId);

    /**
     * 给用户分配角色
     *
     * @param roleDTO
     */
    void setUserRole(RoleDTO roleDTO);

    /**
     * 获取角色分页数据
     *
     * @param currentPage
     * @param pageSize
     * @param roleName
     * @return
     */
    Page<AclRole> getRoleInfoPage(int currentPage, int pageSize, String roleName);

    /**
     * 添加角色信息
     *
     * @param aclRole
     */
    void saveRole(AclRole aclRole);

    /**
     * 修改角色信息
     *
     * @param aclRole
     */
    void updateRole(AclRole aclRole);

    /**
     * 根据 ID 删除角色
     *
     * @param roleId
     */
    void deleteById(Integer roleId);
}
