package com.buka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.enums.AppHttpCodeEnum;
import com.buka.pojo.Comment;
import com.buka.service.CommentService;
import com.buka.mapper.CommentMapper;
import com.buka.service.UserService;
import com.buka.util.MyBeanUtil;
import com.buka.util.ResponseResult;
import com.buka.vo.CommentVo;
import com.buka.vo.PageVo;
import org.omg.CORBA.SystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
* @author SPONGE
* @description 针对表【comment(评论表)】的数据库操作Service实现
* @createDate 2024-06-16 10:54:47
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId,articleId);
        //根评论rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);
        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        //查询所有根评论对应的子评论集合，并且赋值给相应的属性
        for (CommentVo commentVo : commentVoList) {
            List<CommentVo> childrenList = getChildren(commentVo.getId());
            commentVo.setChildren(childrenList);
        }
//        return ResponseResult.ok(new PageVo(commentVoList,page.getTotal()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",page.getTotal());
        hashMap.put("rows",commentVoList);
        return ResponseResult.ok(hashMap);
    }

    /**
     * 根据评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> commentList = list(queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(commentList);
        return commentVoList;
    }
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVoList = MyBeanUtil.copyBeanList(list, CommentVo.class);
        for (CommentVo commentVo : commentVoList) {
            //查询用户的昵称并且赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUserName(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            if (commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVoList;
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论的内容不能为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new RuntimeException("评论不能为空");
        }
        save(comment);
        return ResponseResult.ok();
    }
}




