package com.buka.mapper;

import com.buka.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
* @author SPONGE
* @description 针对表【article(文章表)】的数据库操作Mapper
* @createDate 2024-06-03 12:17:51
* @Entity com.buka.pojo.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




