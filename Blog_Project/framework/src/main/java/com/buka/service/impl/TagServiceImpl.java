package com.buka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.dto.TagListDto;
import com.buka.pojo.Tag;
import com.buka.service.TagService;
import com.buka.mapper.TagMapper;
import com.buka.util.MyBeanUtil;
import com.buka.util.ResponseResult;
import com.buka.vo.AdminTagVo;
import com.buka.vo.TagVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【tag(标签)】的数据库操作Service实现
* @createDate 2024-06-20 14:11:51
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //封装数据返回
        TagVo tagVo = new TagVo(page.getRecords(),page.getTotal());
        return ResponseResult.ok(tagVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = MyBeanUtil.copyBean(tagListDto, Tag.class);
        this.save(tag);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult delTag(Long id) {
        boolean removeById = this.removeById(id);
        if (removeById){
            return ResponseResult.ok();
        }
        throw new RuntimeException("删除失败");
    }

    @Override
    public ResponseResult tagInfo(Long id) {
//        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Tag::getId,id);
        Tag tag = getById(id);
        TagListDto tagListDto = MyBeanUtil.copyBean(tag, TagListDto.class);
        return ResponseResult.ok(tagListDto);
    }

    @Override
    public ResponseResult updateTag(TagListDto tagListDto) {
        Tag tag = MyBeanUtil.copyBean(tagListDto, Tag.class);
        updateById(tag);
        return ResponseResult.ok();
    }

    @Override
    public List<AdminTagVo> listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> tagList = list(queryWrapper);
        List<AdminTagVo> adminTagVos = MyBeanUtil.copyBeanList(tagList, AdminTagVo.class);
        return adminTagVos;
    }
}




