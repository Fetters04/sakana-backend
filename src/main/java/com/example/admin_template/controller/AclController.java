package com.example.admin_template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin_template.entity.Result;
import com.example.admin_template.entity.acl.AclRole;
import com.example.admin_template.entity.acl.AclUser;
import com.example.admin_template.entity.acl.Permission;
import com.example.admin_template.entity.dto.PermissionDTO;
import com.example.admin_template.entity.dto.RoleDTO;
import com.example.admin_template.service.AclRoleService;
import com.example.admin_template.service.AclUserService;
import com.example.admin_template.service.PermissionService;
import com.example.admin_template.service.RolePermissionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fetters
 */
@RestController
@RequestMapping("/acl")
public class AclController {
    @Resource
    private AclUserService aclUserService;
    @Resource
    private AclRoleService aclRoleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 获取用户分页数据
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/user/{currentPage}/{pageSize}")
    public Result<Map<String, Object>> getUserInfoPage(@PathVariable int currentPage,
                                                       @PathVariable int pageSize,
                                                       @RequestParam(required = false) String username) {
        // 获取用户分页数据
        Page<AclUser> page = aclUserService.getUserInfoPage(currentPage, pageSize, username);

        Map<String, Object> data = new HashMap<>();
        data.put("records", page.getRecords());
        data.put("total", page.getTotal());
        data.put("size", page.getSize());
        data.put("current", page.getCurrent());
        data.put("pages", page.getPages());
        data.put("searchCount", true);

        return Result.success(data);
    }

    /**
     * 添加用户信息
     *
     * @param aclUser
     * @return
     */
    @PostMapping("/user/save")
    public Result<Void> saveUser(@RequestBody AclUser aclUser) {
        boolean result = aclUserService.saveUser(aclUser);
        if (result) {
            return Result.success(null);
        } else {
            return Result.fail("添加用户失败，用户名重复");
        }
    }

    /**
     * 修改用户信息
     *
     * @param aclUser
     * @return
     */
    @PutMapping("/user/update")
    public Result<Void> updateUser(@RequestBody AclUser aclUser) {
        boolean result = aclUserService.updateUser(aclUser);
        if (result) {
            return Result.success(null);
        } else {
            return Result.fail("修改用户失败，用户名重复");
        }
    }

    /**
     * 获取所有角色和用户的角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/toAssign/{userId}")
    public Result<Map<String, List<AclRole>>> toAssign(@PathVariable int userId) {
        Map<String, List<AclRole>> data = aclRoleService.getAllRoleAndUserRole(userId);
        return Result.success(data);
    }

    /**
     * 给用户分配角色
     *
     * @param roleDTO
     * @return
     */
    @PostMapping("/user/doAssignRole")
    public Result<Void> doAssignRole(@RequestBody RoleDTO roleDTO) {
        aclRoleService.setUserRole(roleDTO);
        return Result.success(null);
    }

    /**
     * 删除单个用户
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/user/remove/{userId}")
    public Result<Void> removeUser(@PathVariable int userId) {
        aclUserService.deleteById(userId);
        return Result.success(null);
    }

    /**
     * 批量删除用户
     *
     * @param userIds
     * @return
     */
    @DeleteMapping("/user/batchRemove")
    public Result<Void> batchRemoveUser(@RequestBody List<Integer> userIds) {
        aclUserService.deleteByIds(userIds);
        return Result.success(null);
    }

    /**
     * 获取角色分页数据
     *
     * @param currentPage
     * @param pageSize
     * @param roleName
     * @return
     */
    @GetMapping("/role/{currentPage}/{pageSize}")
    public Result<Map<String, Object>> getRoleInfoPage(@PathVariable int currentPage,
                                                       @PathVariable int pageSize,
                                                       @RequestParam(required = false) String roleName) {
        // 获取用户分页数据
        Page<AclRole> page = aclRoleService.getRoleInfoPage(currentPage, pageSize, roleName);

        Map<String, Object> data = new HashMap<>();
        data.put("records", page.getRecords());
        data.put("total", page.getTotal());
        data.put("size", page.getSize());
        data.put("current", page.getCurrent());
        data.put("pages", page.getPages());
        data.put("searchCount", true);

        return Result.success(data);
    }

    /**
     * 添加角色信息
     *
     * @param aclRole
     * @return
     */
    @PostMapping("/role/save")
    public Result<Void> saveRole(@RequestBody AclRole aclRole) {
        aclRoleService.saveRole(aclRole);
        return Result.success(null);
    }

    /**
     * 修改角色信息
     *
     * @param aclRole
     * @return
     */
    @PutMapping("/role/update")
    public Result<Void> updateRole(@RequestBody AclRole aclRole) {
        aclRoleService.updateRole(aclRole);
        return Result.success(null);
    }

    /**
     * 获取所有菜单数据
     *
     * @return
     */
    @GetMapping("/permission")
    public Result<List<Permission>> getAllPermissions() {
        List<Permission> allPermissions = permissionService.getAllPermissions();
        return Result.success(allPermissions);
    }

    /**
     * 新增菜单
     *
     * @param permissionDTO
     * @return
     */
    @PostMapping("/permission/save")
    public Result<Void> savePermission(@RequestBody PermissionDTO permissionDTO) {
        permissionService.savePermission(permissionDTO);
        return Result.success(null);
    }

    /**
     * 修改菜单
     *
     * @param permissionDTO
     * @return
     */
    @PutMapping("/permission/update")
    public Result<Void> updatePermission(@RequestBody PermissionDTO permissionDTO) {
        permissionService.updatePermission(permissionDTO);
        return Result.success(null);
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/permission/remove/{id}")
    public Result<Void> removePermission(@PathVariable int id) {
        permissionService.removePermission(id);
        return Result.success(null);
    }

    /**
     * 根据 roleId 获取菜单数据
     *
     * @param roleId
     * @return
     */
    @GetMapping("/permission/toAssign/{roleId}")
    public Result<List<Permission>> getAllPermissionWithRoleId(@PathVariable int roleId) {
        List<Permission> permissionListByRoleId = permissionService.getAllPermissionsWithRoleId(roleId);
        return Result.success(permissionListByRoleId);
    }

    /**
     * 给角色分配权限
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PostMapping("/permission/doAssign")
    public Result<Void> assignPermission(@RequestParam Integer roleId,
                                         @RequestParam List<Integer> permissionIds) {
        rolePermissionService.assignPermission(roleId, permissionIds);
        return Result.success(null);
    }

    /**
     * 根据 ID 删除角色
     *
     * @param roleId
     * @return
     */
    @DeleteMapping("/role/remove/{roleId}")
    public Result<Void> removeRole(@PathVariable Integer roleId) {
        aclRoleService.deleteById(roleId);
        return Result.success(null);
    }
}
