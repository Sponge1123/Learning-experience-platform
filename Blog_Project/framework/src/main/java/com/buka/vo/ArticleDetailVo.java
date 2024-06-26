package com.buka.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 文章内容详情返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;
    private String content;
    private String isComment;
    private String isTop;
    private String title;
    private Long categoryId;
    private String categoryName;
    private Date createTime;
    private Long viewCount;
}
