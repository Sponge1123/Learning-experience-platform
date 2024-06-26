package com.buka.service;

import com.buka.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buka.util.ResponseResult;

/**
* @author SPONGE
* @description 针对表【comment(评论表)】的数据库操作Service
* @createDate 2024-06-16 10:54:47
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
