package com.buka.controller;

import com.buka.service.ArticleService;
import com.buka.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "文章",description = "文章管理")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @ApiOperation(value = "热门文章列表")
    /**
     * 查询热门文章
     */
    @GetMapping("/article/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    /**
     * 查询文章列表
     * @param categoryId 分类id
     * @param pageNum 当前页
     * @param pageSize 条数
     * @return
     */
    @GetMapping("/article/articleList")
    private ResponseResult articleList(int categoryId,int pageNum,int pageSize){
        return articleService.articleList(categoryId,pageNum,pageSize);
    }

    /**
     * 查看文章内容详情
     * @param id 文章id
     * @return
     */
    @GetMapping("/article/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    /**
     * 获取文章的浏览量
     */
    @PutMapping("/article/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
