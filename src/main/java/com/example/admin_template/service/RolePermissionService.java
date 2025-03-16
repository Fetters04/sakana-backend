package com.example.admin_template.service;

import java.util.List;

/**
 * role_permission关联表 Service
 *
 * @author Fetters
 */
public interface RolePermissionService {
    /**
     * 给角色分配权限
     *
     * @param roleId
     * @param permissionIds
     */
    void assignPermission(Integer roleId, List<Integer> permissionIds);
}
