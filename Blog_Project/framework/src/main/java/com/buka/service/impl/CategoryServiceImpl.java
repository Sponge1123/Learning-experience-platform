package com.buka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.constants.SystemConstants;
import com.buka.dto.CategoryDto;
import com.buka.dto.UpdateCatDto;
import com.buka.pojo.Article;
import com.buka.pojo.Category;
import com.buka.service.ArticleService;
import com.buka.service.CategoryService;
import com.buka.mapper.CategoryMapper;
import com.buka.util.MyBeanUtil;
import com.buka.util.ResponseResult;
import com.buka.vo.CategoryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author SPONGE
* @description 针对表【category(分类表)】的数据库操作Service实现
* @createDate 2024-06-03 13:22:12
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Article::getCategoryId);
        wrapper.eq(Article::getStatus,0);
        wrapper.groupBy(Article::getCategoryId);
        List<Article> articleList = articleService.list(wrapper);
        List<Long> categoryIds = articleList.stream().map(article -> article.getCategoryId()).collect(Collectors.toList());
        List<Category> categoryList = listByIds(categoryIds);
        List<CategoryVo> categoryVoList = categoryList.stream().map(category -> MyBeanUtil.copyBean(category, CategoryVo.class)).collect(Collectors.toList());
        return ResponseResult.ok(categoryVoList);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> categoryList = list(queryWrapper);
        List<CategoryVo> categoryVoList = MyBeanUtil.copyBeanList(categoryList, CategoryVo.class);
        return categoryVoList;
    }

    @Override
    public ResponseResult searchCatList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isAllBlank(name,status)){
            if (StringUtils.isNotBlank(name)){
                queryWrapper.like(Category::getName,name);
            }
            if (StringUtils.isNotBlank(status)){
                queryWrapper.eq(Category::getStatus,status);
            }
        }
        Page<Category> page = categoryService.page(new Page<>(pageNum, pageSize), queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",page.getRecords());
        map.put("total",page.getTotal());
        return ResponseResult.ok(map);
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        Category category = MyBeanUtil.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult searchCategoryById(Long id) {
        Category category = getById(id);
        return ResponseResult.ok(category);
    }

    @Override
    public ResponseResult updateCategory(UpdateCatDto updateCatDto) {
        Category category = MyBeanUtil.copyBean(updateCatDto, Category.class);
        updateById(category);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.ok();
    }
}




