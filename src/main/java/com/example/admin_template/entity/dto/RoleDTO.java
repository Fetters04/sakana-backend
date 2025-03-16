package com.example.admin_template.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Fetters
 */
@Data
public class RoleDTO {
    private int userId;
    private List<Integer> roleIdList;
}
