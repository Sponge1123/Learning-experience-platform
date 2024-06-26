package com.buka.service;

import com.buka.dto.TagListDto;
import com.buka.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buka.util.ResponseResult;
import com.buka.vo.AdminTagVo;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【tag(标签)】的数据库操作Service
* @createDate 2024-06-20 14:11:51
*/
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult delTag(Long id);

    ResponseResult tagInfo(Long id);

    ResponseResult updateTag(TagListDto tagListDto);

    List<AdminTagVo> listAllTag();

}
