package com.buka.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Article的返回包装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {
    private Long id;
    private String title;
    private Long viewCount;
    private String summary;
    private Long categoryId;
    private String thumbnail;
    private String categoryName;
    private Date createTime;
}
