package com.buka.controller;

import com.buka.pojo.Comment;
import com.buka.service.CommentService;
import com.buka.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    /**
     * 查看评论列表
     * @param articleId 文章id
     * @param pageNum 页面
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/comment/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(articleId,pageNum,pageSize);
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @PostMapping("/comment")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
