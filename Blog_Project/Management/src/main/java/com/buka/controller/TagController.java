package com.buka.controller;

import com.buka.dto.TagListDto;
import com.buka.pojo.Tag;
import com.buka.service.TagService;
import com.buka.util.ResponseResult;
import com.buka.vo.AdminTagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 查询标签
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    @GetMapping("/content/tag/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
       return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }
    /**
     * 新增标签
     */
    @PostMapping("/content/tag")
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }
    /**
     * 删除标签
     */
    @DeleteMapping("/content/tag/{id}")
    private ResponseResult delTag(@PathVariable("id") Long id){
        return tagService.delTag(id);
    }
    /**
     * 获取标签信息
     */
    @GetMapping("/content/tag/{id}")
    public ResponseResult tagInfo(@PathVariable("id") Long id){
        return tagService.tagInfo(id);
    }
    /**
     * 修改标签
     */
    @PutMapping("/content/tag")
    public ResponseResult updateTag(@RequestBody TagListDto tagListDto){
        return tagService.updateTag(tagListDto);
    }
    /**
     * 查询所有标签(Admin)
     */
    @GetMapping("/content/tag/listAllTag")
    public ResponseResult listAllTag(){
        List<AdminTagVo> tagList = tagService.listAllTag();
        return ResponseResult.ok(tagList);
    }
}
