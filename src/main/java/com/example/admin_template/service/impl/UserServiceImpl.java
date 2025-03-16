package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.Result;
import com.example.admin_template.entity.User;
import com.example.admin_template.entity.acl.AclRole;
import com.example.admin_template.entity.acl.AclUser;
import com.example.admin_template.entity.acl.Permission;
import com.example.admin_template.entity.acl.RolePermission;
import com.example.admin_template.entity.vo.UserInfoVO;
import com.example.admin_template.mapper.*;
import com.example.admin_template.service.UserService;
import com.example.admin_template.utils.TokenGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fetters
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AclUserMapper aclUserMapper;
    @Resource
    private AclRoleMapper aclRoleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Result<String> login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);

        if (user != null && user.getPassword().equals(password)) {
            // 为用户生成 token
            String token = TokenGenerator.generateToken();
            user.setToken(token);

            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", user.getUserId());
            updateWrapper.set("token", token);
            userMapper.update(null, updateWrapper);

            return new Result<>(200, "成功", user.getToken(), true);
        }
        return new Result<>(201, "失败", "用户名或密码错误", false);
    }

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result<UserInfoVO> getUserInfo(String token) {
        // 根据 token 获取用户对象
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        if (user != null) {
            Integer userId = user.getUserId();
            // 从acl_user表中获取角色，赋值给user对象
            AclUser aclUser = aclUserMapper.selectById(userId);
            String roleName = aclUser.getRoleName();
            if (roleName != null) {
                List<String> roleNameList = Arrays.stream(roleName.split(",")).toList();
                user.setRoles(roleNameList);

                /**
                 * 根据关联的角色从acl_role表中获取对应角色ID，然后去role_permission表中拿到permission表的ID，
                 * 然后去permission表中查权限，并且level为2,3的归为routes，level为4的归为buttons
                 */

                // 根据角色名查询角色 ID 列表
                List<Integer> roleIds = aclRoleMapper.selectList(
                                new QueryWrapper<AclRole>().in("role_name", roleNameList)
                        ).stream()
                        .map(AclRole::getId)
                        .collect(Collectors.toList());

                // 查询这些角色的权限ID
                List<Integer> permissionIds = rolePermissionMapper.selectList(
                                new QueryWrapper<RolePermission>()
                                        .in("role_id", roleIds)
                                        .select("permission_id")
                        )
                        .stream()
                        // 去重
                        .distinct()
                        // 获取权限 ID
                        .map(RolePermission::getPermissionId)
                        .collect(Collectors.toList());

                if (!permissionIds.isEmpty()) {
                    // 根据权限 ID 查询权限详情
                    List<Permission> allPermissions = permissionMapper.selectPermissionsByIds(permissionIds);

                    // 将权限按 level 分类
                    List<String> routes = new ArrayList<>();
                    List<String> buttons = new ArrayList<>();

                    for (Permission permission : allPermissions) {
                        if (permission.getLevel() == 4) {
                            buttons.add(permission.getCode());
                        } else if (permission.getLevel() > 1) {
                            routes.add(permission.getCode());
                        }
                    }

                    // 将权限分配给用户对象
                    user.setRoutes(routes);
                    user.setButtons(buttons);
                }
            }

            // 封装VO对象
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setAvatar(user.getAvatar());
            userInfoVO.setUsername(user.getUsername());
            userInfoVO.setRoles(user.getRoles());
            userInfoVO.setButtons(user.getButtons());
            userInfoVO.setRoutes(user.getRoutes());

            return new Result<>(200, "成功", userInfoVO, true);
        }
        return new Result<>(201, "失败", null, false);
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @Override
    public Result<String> logout(String token) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            user.setToken(null);

            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", user.getUserId());
            updateWrapper.set("token", user.getToken());
            userMapper.update(null, updateWrapper);

            return new Result<>(200, "成功", null, true);
        }
        return new Result<>(201, "失败", null, false);
    }
}




