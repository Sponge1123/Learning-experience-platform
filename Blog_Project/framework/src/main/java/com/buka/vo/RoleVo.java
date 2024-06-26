package com.buka.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    private Long id;
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    //菜单id
    private List<String> menuIds;
    private String remark;
}
