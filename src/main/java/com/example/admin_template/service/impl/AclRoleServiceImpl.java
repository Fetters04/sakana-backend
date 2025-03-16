package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.acl.AclRole;
import com.example.admin_template.entity.acl.AclUser;
import com.example.admin_template.entity.dto.RoleDTO;
import com.example.admin_template.mapper.AclRoleMapper;
import com.example.admin_template.mapper.AclUserMapper;
import com.example.admin_template.mapper.RolePermissionMapper;
import com.example.admin_template.service.AclRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Fetters
 * @description 针对表【acl_roles】的数据库操作Service实现
 * @createDate 2025-02-21 21:17:39
 */
@Service
public class AclRoleServiceImpl extends ServiceImpl<AclRoleMapper, AclRole>
        implements AclRoleService {
    @Resource
    private AclUserMapper aclUserMapper;
    @Resource
    private AclRoleMapper aclRoleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 获取所有角色和用户的角色
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, List<AclRole>> getAllRoleAndUserRole(int userId) {
        // 所有角色信息
        List<AclRole> allRolesList = aclRoleMapper.selectList(null);

        QueryWrapper<AclUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        // 获取到用户角色
        String roleNames = aclUserMapper.selectOne(queryWrapper).getRoleName();
        List<AclRole> assignRolesList = new ArrayList<>();
        if (roleNames != null && !roleNames.isEmpty()) {
            // 处理为List<AclRole>
            String[] roles = roleNames.split(",");
            for (String role : roles) {
                QueryWrapper<AclRole> roleWrapper = new QueryWrapper<>();
                roleWrapper.eq("role_name", role);
                AclRole aclRole = aclRoleMapper.selectOne(roleWrapper);
                assignRolesList.add(aclRole);
            }
        }

        // 封装为map返回
        Map<String, List<AclRole>> map = new HashMap<>();
        map.put("assignRolesList", assignRolesList);
        map.put("allRolesList", allRolesList);

        return map;
    }

    /**
     * 给用户分配角色
     *
     * @param roleDTO
     */
    @Override
    public void setUserRole(RoleDTO roleDTO) {
        // 获取user对象
        AclUser aclUser = aclUserMapper.selectById(roleDTO.getUserId());

        if (roleDTO.getRoleIdList() != null && !roleDTO.getRoleIdList().isEmpty()) {
            // 根据角色ID列表查出所有角色名，拼接成字符串
            List<String> roleNames = new ArrayList<>();
            QueryWrapper<AclRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", roleDTO.getRoleIdList());
            List<AclRole> aclRoles = aclRoleMapper.selectList(queryWrapper);
            for (AclRole aclRole : aclRoles) {
                roleNames.add(aclRole.getRoleName());
            }
            String userRole = String.join(",", roleNames);

            // 重新设置用户角色并修改到数据库中
            aclUser.setRoleName(userRole);
            aclUserMapper.updateById(aclUser);
        } else {
            aclUser.setRoleName(null);
            aclUserMapper.updateById(aclUser);
        }
    }

    /**
     * 获取角色分页数据
     *
     * @param currentPage
     * @param pageSize
     * @param roleName
     * @return
     */
    @Override
    public Page<AclRole> getRoleInfoPage(int currentPage, int pageSize, String roleName) {
        // 创建分页对象
        Page<AclRole> page = new Page<>(currentPage, pageSize);
        // 创建查询条件包装器
        QueryWrapper<AclRole> queryWrapper = new QueryWrapper<>();
        // 如果传入了角色名，添加模糊查询条件
        if (roleName != null && !roleName.isEmpty()) {
            queryWrapper.like("role_name", roleName);
        }
        // 调用 baseMapper 的 selectPage 方法进行分页查询
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 添加角色信息
     *
     * @param aclRole
     */
    @Override
    public void saveRole(AclRole aclRole) {
        this.save(aclRole);
    }

    /**
     * 修改角色信息
     *
     * @param aclRole
     */
    @Override
    public void updateRole(AclRole aclRole) {
        this.updateById(aclRole);
    }

    /**
     * 根据 ID 删除角色
     *
     * @param roleId
     */
    @Override
    public void deleteById(Integer roleId) {
        // 删除关联表数据
        rolePermissionMapper.deletePermissionsByRoleId(roleId);
        // 删除角色数据
        aclRoleMapper.deleteById(roleId);
    }
}




