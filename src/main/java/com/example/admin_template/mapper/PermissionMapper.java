package com.example.admin_template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin_template.entity.acl.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Fetters
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    // 查询所有菜单及其子菜单
    List<Permission> findAllWithChildren();
    // 查询某菜单下的子菜单
    List<Permission> findChildrenByPid(Integer pid);

    @Select("""
        SELECT p.id
        FROM permission p
        JOIN role_permission rp ON p.id = rp.permission_id
        WHERE rp.role_id = #{roleId}
    """)
    List<Integer> selectIdsByRoleId(int roleId);

    List<Permission> selectPermissionsByIds(List<Integer> permissionIds);
}