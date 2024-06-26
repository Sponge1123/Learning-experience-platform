package com.buka.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章表
 * @TableName article
 */
@TableName(value ="article")
public class Article implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 文章内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 文章摘要
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 所属分类id
     */
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * 缩略图
     */
    @TableField(value = "thumbnail")
    private String thumbnail;

    /**
     * 是否置顶（0否，1是）
     */
    @TableField(value = "is_top")
    private String isTop;

    /**
     * 状态（0已发布，1草稿）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 访问量
     */
    @TableField(value = "view_count")
    private Long viewCount;

    /**
     * 是否允许评论 1是，0否
     */
    @TableField(value = "is_comment")
    private String isComment;

    /**
     * 
     */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private Long updateBy;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField(value = "del_flag")
    @TableLogic
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 文章内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 文章内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 文章摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 文章摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 所属分类id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 所属分类id
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 缩略图
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 缩略图
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 是否置顶（0否，1是）
     */
    public String getIsTop() {
        return isTop;
    }

    /**
     * 是否置顶（0否，1是）
     */
    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    /**
     * 状态（0已发布，1草稿）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态（0已发布，1草稿）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 访问量
     */
    public Long getViewCount() {
        return viewCount;
    }

    /**
     * 访问量
     */
    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * 是否允许评论 1是，0否
     */
    public String getIsComment() {
        return isComment;
    }

    /**
     * 是否允许评论 1是，0否
     */
    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    /**
     * 
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * 
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}