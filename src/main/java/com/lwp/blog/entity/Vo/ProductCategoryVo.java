package com.lwp.blog.entity.Vo;

import com.lwp.blog.utils.invalid.category.IsRepeatName;
import com.lwp.blog.utils.invalid.category.CategoryValidationGroups;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/22/9:22
 * @Description:
 */
public class ProductCategoryVo {
    private String id;

    @NotBlank(groups = {CategoryValidationGroups.GroupCategoryAdd.class},message = "商品分类名称不能为空")
    @IsRepeatName(groups = {CategoryValidationGroups.GroupCategoryAdd.class},message = "商品分类名称已存在")
    private String name;

    @NotBlank(groups = {CategoryValidationGroups.GroupCategoryCommon.class},message = "父类不能为空")
    private String parentId;

    private String level;
    private String attachmentId;

    @NotBlank(groups = {CategoryValidationGroups.GroupCategoryCommon.class},message = "状态不能为空，选择启用/不启用")
    private String status;

    private String isDelete;
    private String remark;
    private Date createTime;
    private String createUserId;
    private Date updateTime;
    private String updateUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
