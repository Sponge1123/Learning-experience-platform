package com.buka.operation;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buka.pojo.Article;
import com.buka.service.ArticleService;
import com.buka.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 实现缓存预热，在项目启动时进行预处理
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    public ArticleService articleService;
    @Autowired
    public RedisUtil redisUtil;
    @Override
    public void run(String... args) throws Exception {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,0);
        List<Article> articleList = articleService.list(queryWrapper);
        Map<String,Object> stringMap = articleList.stream().collect(Collectors.toMap(article -> String.valueOf(article.getId()), article -> article.getViewCount()));
        redisUtil.hSet("article:view_count",stringMap);
    }
}
