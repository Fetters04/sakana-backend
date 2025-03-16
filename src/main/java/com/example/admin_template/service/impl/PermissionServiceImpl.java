package com.example.admin_template.service.impl;

import com.example.admin_template.entity.acl.Permission;
import com.example.admin_template.entity.dto.PermissionDTO;
import com.example.admin_template.mapper.PermissionMapper;
import com.example.admin_template.service.PermissionService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fetters
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 获取所有菜单数据
     *
     * @return
     */
    @Override
    public List<Permission> getAllPermissions() {
        return permissionMapper.findAllWithChildren();
    }

    /**
     * 新增菜单
     *
     * @param permissionDTO
     */
    @Override
    public void savePermission(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDTO, permission);
        permissionMapper.insert(permission);
    }

    /**
     * 修改菜单
     *
     * @param permissionDTO
     */
    @Override
    public void updatePermission(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDTO, permission);
        permissionMapper.updateById(permission);
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    @Override
    public void removePermission(int id) {
        // 删除子菜单
        List<Permission> children = permissionMapper.findChildrenByPid(id);
        if (children != null && !children.isEmpty()) {
            for (Permission child : children) {
                removePermission(child.getId());
            }
        }
        // 删除当前菜单
        permissionMapper.deleteById(id);
    }

    /**
     * 根据 roleId 获取菜单数据
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> getAllPermissionsWithRoleId(int roleId) {
        // 查询所有权限
        List<Permission> allPermissions = getAllPermissions();
        // 查询指定角色关联的权限ID
        List<Integer> selectedIds = permissionMapper.selectIdsByRoleId(roleId);

        // 递归设置 select
        setPermissionSelect(allPermissions, selectedIds);

        return allPermissions;
    }

    /**
     * 递归设置权限的 select 状态
     */
    private void setPermissionSelect(List<Permission> permissions, List<Integer> selectedIds) {
        if (permissions == null || permissions.isEmpty()) {
            return;
        }

        for (Permission permission : permissions) {
            // 判断当前权限是否在选中的ID列表中
            if (selectedIds.contains(permission.getId())) {
                permission.setSelect(true);
            }
            // 递归处理子权限
            setPermissionSelect(permission.getChildren(), selectedIds);
        }
    }
}