package com.buka.controller;

import com.alibaba.excel.EasyExcel;
import com.buka.dto.CategoryDto;
import com.buka.dto.UpdateCatDto;
import com.buka.enums.AppHttpCodeEnum;
import com.buka.pojo.Category;
import com.buka.service.CategoryService;
import com.buka.util.MyBeanUtil;
import com.buka.util.ResponseResult;
import com.buka.util.WebUtil;
import com.buka.vo.CategoryVo;
import com.buka.vo.ExcelCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 查询所有分类
     */
    @GetMapping("/content/category/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> categoryVoList = categoryService.listAllCategory();
        return ResponseResult.ok(categoryVoList);
    }
    /**
     * 导出为Excel
     */
    @GetMapping("/content/category/export")
    public void export(HttpServletResponse response){
        //设置下载的请求头
        try {
            WebUtil.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryList = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = MyBeanUtil.copyBeanList(categoryList, ExcelCategoryVo.class);
            //把数据写入excel
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询分类列表
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param name 分类名
     * @param status 状态
     * @return
     */
    @GetMapping("/content/category/list")
    public ResponseResult searchCatList(Integer pageNum,Integer pageSize,String name,String status){
        return categoryService.searchCatList(pageNum,pageSize,name,status);
    }

    /**
     * 新增分类
     * @param categoryDto
     * @return
     */
    @PostMapping("/content/category")
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @GetMapping("/content/category/{id}")
    public ResponseResult searchCategoryById(@PathVariable("id") Long id){
        return categoryService.searchCategoryById(id);
    }

    /**
     * 更新分类
     * @param updateCatDto
     * @return
     */
    @PutMapping("/content/category")
    public ResponseResult updateCategory(@RequestBody UpdateCatDto updateCatDto){
        return categoryService.updateCategory(updateCatDto);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping("/content/category/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }
}
