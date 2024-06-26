package com.buka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.dto.AddArticleDto;
import com.buka.dto.ArticleListDto;
import com.buka.enums.AppHttpCodeEnum;
import com.buka.pojo.Article;
import com.buka.pojo.ArticleTag;
import com.buka.pojo.Category;
import com.buka.service.ArticleService;
import com.buka.mapper.ArticleMapper;
import com.buka.service.ArticleTagService;
import com.buka.service.CategoryService;
import com.buka.util.MyBeanUtil;
import com.buka.util.RedisUtil;
import com.buka.util.ResponseResult;
import com.buka.vo.ArticleDetailVo;
import com.buka.vo.ArticleVo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author SPONGE
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2024-06-03 12:17:51
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getViewCount);
        lambdaQueryWrapper.last("limit 10");
        List<Article> articleList = list(lambdaQueryWrapper);
        List<ArticleVo> articleVoList = articleList.stream().map(article -> MyBeanUtil.copyBean(article, ArticleVo.class)).collect(Collectors.toList());
        return ResponseResult.ok(articleVoList);
    }

    @Override
    public ResponseResult articleList(int categoryId, int pageNum, int pageSize) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus,0);
        wrapper.orderByDesc(Article::getIsTop);
        if (categoryId !=0){
            wrapper.eq(Article::getCategoryId,categoryId);
        }
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        Page<Article> page = page(articlePage, wrapper);
        List<Article> records = page.getRecords();
        List<ArticleVo> articleVoList = records.stream().map(article -> MyBeanUtil.copyBean(article, ArticleVo.class)).collect(Collectors.toList());
        for (ArticleVo articleVo : articleVoList) {
            LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Category::getId,articleVo.getCategoryId());
            Category category = categoryService.getOne(queryWrapper);
            articleVo.setCategoryName(category.getName());
        }
        HashMap<String, Object> ResultMap = new HashMap<>();
        ResultMap.put("total",page.getTotal());
        ResultMap.put("rows",articleVoList);
        return ResponseResult.ok(ResultMap);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        //Integer viewCount = (Integer) redisUtil.hGet("article:view_count", id.toString());
        //article.setViewCount(Long.valueOf(viewCount));
        ArticleDetailVo articleDetailVo = MyBeanUtil.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Category category = categoryService.getById(article.getCategoryId());
        articleDetailVo.setCategoryName(category.getName());
        return ResponseResult.ok(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新Redis中对应的id浏览量
        redisUtil.incrementCacheMapValue("article:view_count", String.valueOf(id),1);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult addArticle(AddArticleDto articleDto) {
        //保存博客
        Article article = MyBeanUtil.copyBean(articleDto, Article.class);
        save(article);
        List<ArticleTag> articleTagList = articleDto.getTags().stream().map(tagId -> new ArticleTag(article.getId(), tagId)).collect(Collectors.toList());
        //添加博客和标签的关系
        articleTagService.saveBatch(articleTagList);
        return ResponseResult.ok();
    }

//    @Override
//    public ResponseResult searchArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if (StringUtils.isNotBlank(title)){
//            queryWrapper.like(Article::getTitle,title);
//        }
//        if (StringUtils.isNotBlank(summary)){
//            queryWrapper.like(Article::getSummary,summary);
//        }
//        Page<Article> page = articleService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("rows",page.getRecords());
//        map.put("total",page.getTotal());
//        return ResponseResult.ok(map);
//    }
    public ResponseResult searchArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        SearchRequest searchRequest = new SearchRequest("article"); // 替换为你的索引名
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from((pageNum - 1) * pageSize).size(pageSize);
        if (title != null && !title.isEmpty()) {
            sourceBuilder.query(QueryBuilders.matchQuery("title", title));
        }
        if (summary != null && !summary.isEmpty()) {
            sourceBuilder.query(QueryBuilders.matchQuery("summary", summary));
        }
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] hits = searchHits.getHits();
            HashMap<String, Object> map = new HashMap<>();
            map.put("rows", hits);
            map.put("total", searchHits.getTotalHits().value); // 获取总条数
            return ResponseResult.ok(map);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
    @Override
    public ResponseResult searchArticleById(Long id) {
        Article article = getById(id);
        return ResponseResult.ok(article);
    }

    @Override
    public ResponseResult updateArticle(Article article) {
        boolean b = updateById(article);
        if (b){
            return ResponseResult.ok();
        }
        else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult deleteArticleById(Long id) {
        boolean b = removeById(id);
        if (b){
            return ResponseResult.ok();
        }
        else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}




