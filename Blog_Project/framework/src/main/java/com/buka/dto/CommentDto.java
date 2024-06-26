package com.buka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long articleId;
    private Long rootId;
    private String content;
    private Long toCommentUserId;
    private Long toCommentId;
    private Long createBy;
    private Date createTime;
    private String toCommentUserName;
    private String UserName;

}
