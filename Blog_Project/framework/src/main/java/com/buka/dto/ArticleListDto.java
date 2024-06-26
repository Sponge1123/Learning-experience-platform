package com.buka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    private Long id;
    private String content;
    private String title;
    private Long viewCount;
    private String summary;
    private Long categoryId;
    private String thumbnail;
    private String isTop;
    private String status;
    private Date createTime;
    private String isComment;
}
