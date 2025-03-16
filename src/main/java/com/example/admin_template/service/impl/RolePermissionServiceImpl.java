package com.example.admin_template.service.impl;

import com.example.admin_template.mapper.RolePermissionMapper;
import com.example.admin_template.service.RolePermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * role_permission关联表 ServiceImpl
 *
 * @author Fetters
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 给角色分配权限
     *
     * @param roleId
     * @param permissionIds
     */
    @Override
    @Transactional
    public void assignPermission(Integer roleId, List<Integer> permissionIds) {
        // 1. 删除当前角色已有的权限
        rolePermissionMapper.deletePermissionsByRoleId(roleId);

        // 2. 插入新权限数据
        if (permissionIds != null && !permissionIds.isEmpty()) {
            rolePermissionMapper.insertPermissions(roleId, permissionIds);
        }
    }
}
