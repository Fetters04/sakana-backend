package com.example.admin_template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin_template.entity.acl.AclRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Fetters
 * @description 针对表【acl_roles】的数据库操作Mapper
 * @createDate 2025-02-21 21:17:39
 * @Entity com.example.admin_template.entity.acl.AclRoles
 */
public interface AclRoleMapper extends BaseMapper<AclRole> {
}




