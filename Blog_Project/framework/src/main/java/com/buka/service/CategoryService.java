package com.buka.service;

import com.buka.dto.CategoryDto;
import com.buka.dto.UpdateCatDto;
import com.buka.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buka.util.ResponseResult;
import com.buka.vo.CategoryVo;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【category(分类表)】的数据库操作Service
* @createDate 2024-06-03 13:22:12
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult searchCatList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(CategoryDto categoryDto);

    ResponseResult searchCategoryById(Long id);

    ResponseResult updateCategory(UpdateCatDto updateCatDto);

    ResponseResult deleteCategory(Long id);
}
