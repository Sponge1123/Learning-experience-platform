package com.buka.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTagVo {
    private Long id;
    //标签名
    private String name;
}
