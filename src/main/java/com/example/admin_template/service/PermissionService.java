package com.example.admin_template.service;

import com.example.admin_template.entity.acl.Permission;
import com.example.admin_template.entity.dto.PermissionDTO;

import java.util.List;

/**
 * @author Fetters
 */
public interface PermissionService {
    /**
     * 获取所有菜单数据
     *
     * @return
     */
    List<Permission> getAllPermissions();

    /**
     * 新增菜单
     *
     * @param permissionDTO
     */
    void savePermission(PermissionDTO permissionDTO);

    /**
     * 修改菜单
     *
     * @param permissionDTO
     */
    void updatePermission(PermissionDTO permissionDTO);

    /**
     * 删除菜单
     *
     * @param id
     */
    void removePermission(int id);

    /**
     * 根据 roleId 获取菜单数据
     *
     * @param roleId
     * @return
     */
    List<Permission> getAllPermissionsWithRoleId(int roleId);
}