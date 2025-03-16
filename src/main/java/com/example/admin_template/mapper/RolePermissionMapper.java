package com.example.admin_template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin_template.entity.acl.RolePermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 操作 role_permission表 mapper
 *
 * @author Fetters
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 删除指定角色的所有权限
     *
     * @param roleId
     */
    @Delete("DELETE FROM role_permission WHERE role_id = #{roleId}")
    void deletePermissionsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 插入指定角色的新权限
     *
     * @param roleId
     * @param permissionIds
     */
    @Insert("""
                <script>
                    INSERT INTO role_permission (role_id, permission_id, create_time, update_time)
                    VALUES
                    <foreach collection='permissionIds' item='pid' separator=','>
                        (#{roleId}, #{pid}, NOW(), NOW())
                    </foreach>
                </script>
            """)
    void insertPermissions(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> permissionIds);

    /**
     * 根据角色ids获取权限ids
     *
     * @param roleIds
     * @return
     */
    @Select("""
                SELECT permission_id 
                FROM role_permission 
                WHERE role_id IN 
                <foreach collection='roleIds' item='roleId' separator=',' open='(' close=')'>
                    #{roleId}
                </foreach>
            """)
    List<Integer> selectPermissionIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
