package com.buka.controller;

import com.buka.dto.AddArticleDto;
import com.buka.pojo.Article;
import com.buka.service.ArticleService;
import com.buka.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章
     * @param addArticleDto
     * @return
     */
    @PostMapping("/content/article")
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    /**
     * 查询文章列表 ->改造es
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param title 文章标题
     * @param summary 文章摘要
     * @return
     */
    @GetMapping("/content/article/list")
    public ResponseResult searchArticleList(Integer pageNum,Integer pageSize,String title,String summary){
        return articleService.searchArticleList(pageNum,pageSize,title,summary);
    }

    /**
     * 根据id获取文章详情
     * @param id
     * @return
     */
    @GetMapping("/content/article/{id}")
    ///content/article/1
    public ResponseResult searchArticleById(@PathVariable("id") Long id){
        return articleService.searchArticleById(id);
    }

    /**
     * 修改文章内容
     * @param article
     * @return
     */
    @PutMapping("/content/article")
    public ResponseResult updateArticle(@RequestBody Article article){
        return articleService.updateArticle(article);
    }

    /**
     * 根据id删除对应的文章，逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("/content/article/{id}")
    public ResponseResult deleteArticleById(@PathVariable("id") Long id){
        return articleService.deleteArticleById(id);
    }
}
