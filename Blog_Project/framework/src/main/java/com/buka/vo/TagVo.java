package com.buka.vo;

import com.buka.pojo.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private List<Tag> rows;
    private Long total;
}
