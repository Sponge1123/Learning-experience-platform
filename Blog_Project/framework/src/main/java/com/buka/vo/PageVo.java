package com.buka.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {
    private List<CommentVo> commentVoList;
    private Long total;
}
