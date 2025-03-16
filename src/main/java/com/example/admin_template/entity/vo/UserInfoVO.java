package com.example.admin_template.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Fetters
 */
@Data
public class UserInfoVO {
    private String username;
    private String avatar;
    private List<String> roles;
    private List<String> buttons;
    private List<String> routes;
}
