package com.example.admin_template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin_template.entity.User;
import com.example.admin_template.entity.acl.AclUser;
import com.example.admin_template.mapper.UserMapper;
import com.example.admin_template.service.AclUserService;
import com.example.admin_template.mapper.AclUserMapper;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fetters
 */
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser>
        implements AclUserService {

    @Resource
    private AclUserMapper aclUserMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 获取用户分页数据
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<AclUser> getUserInfoPage(int currentPage, int pageSize, String username) {
        // 创建分页对象
        Page<AclUser> page = new Page<>(currentPage, pageSize);
        // 创建查询条件包装器
        QueryWrapper<AclUser> queryWrapper = new QueryWrapper<>();
        // 如果传入了用户名，添加模糊查询条件
        if (username != null && !username.isEmpty()) {
            queryWrapper.like("username", username);
        }
        // 调用 baseMapper 的 selectPage 方法进行分页查询
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 添加用户信息
     *
     * @param aclUser
     * @return
     */
    @Override
    public boolean saveUser(AclUser aclUser) {
        try {
            // 执行保存操作
            boolean isSaved = this.save(aclUser);
            if (isSaved) {
                // 插入成功后，获取插入的 aclUser 的 id
                Integer userId = aclUser.getId();
                // 同步到user表中
                User user = new User();
                user.setUserId(userId);
                user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
                user.setUsername(aclUser.getUsername());
                user.setPassword(aclUser.getPassword());
                userMapper.insert(user);
            }
            return isSaved;
        } catch (DuplicateKeyException e) {
            // 捕获唯一约束违反异常
            return false;
        }
    }

    /**
     * 修改用户信息
     *
     * @param aclUser
     * @return
     */
    @Override
    public boolean updateUser(AclUser aclUser) {
        try {
            return this.updateById(aclUser);
        } catch (DuplicateKeyException e) {
            // 捕获唯一约束违反异常
            return false;
        }
    }

    /**
     * 根据 id 删除单个用户
     *
     * @param userId
     */
    @Override
    public void deleteById(int userId) {
        aclUserMapper.deleteById(userId);
        // 同步删除user表数据
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        userMapper.delete(queryWrapper);
    }

    /**
     * 根据多个 id 批量删除用户
     *
     * @param userIds
     */
    @Override
    public void deleteByIds(List<Integer> userIds) {
        aclUserMapper.deleteByIds(userIds);
        // 同步删除user表数据
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        userMapper.delete(queryWrapper);
    }
}




