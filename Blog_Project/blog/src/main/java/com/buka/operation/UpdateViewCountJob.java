package com.buka.operation;

import com.buka.pojo.Article;
import com.buka.service.ArticleService;
import com.buka.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;
    /**
     * 通过定时任务，每过10分钟把Redis中的浏览量更新到数据库中
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void UpdateViewCountJob(){
        Map<Object, Object> viewCount = redisUtil.hGet("article:view_count");
        ArrayList<Article> articleArrayList = new ArrayList<>();
        for (Object s : viewCount.keySet()){
            Integer count = (Integer) viewCount.get(s);
            Article article = new Article();
            article.setId((Long) s);
            article.setViewCount(Long.valueOf(count));
            articleArrayList.add(article);
        }
        articleService.saveBatch(articleArrayList);
    }
}
