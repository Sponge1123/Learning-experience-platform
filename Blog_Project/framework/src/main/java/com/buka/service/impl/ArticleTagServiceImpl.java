package com.buka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.pojo.ArticleTag;
import com.buka.service.ArticleTagService;
import com.buka.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author SPONGE
* @description 针对表【article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2024-06-21 15:48:35
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




