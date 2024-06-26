package com.buka.vo;

import com.buka.pojo.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {
    private List<MenuVo> children;
    private Long id;
    private String label;
    private Long parentId;
}
