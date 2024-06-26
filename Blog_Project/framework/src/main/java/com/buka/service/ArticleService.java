package com.buka.service;

import com.buka.dto.AddArticleDto;
import com.buka.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buka.util.ResponseResult;

/**
* @author SPONGE
* @description 针对表【article(文章表)】的数据库操作Service
* @createDate 2024-06-03 12:17:51
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();


    ResponseResult articleList(int categoryId, int pageNum, int pageSize);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult searchArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult searchArticleById(Long id);

    ResponseResult updateArticle(Article article);

    ResponseResult deleteArticleById(Long id);
}
