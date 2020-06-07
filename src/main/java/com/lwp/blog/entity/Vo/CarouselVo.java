package com.lwp.blog.entity.Vo;

import com.lwp.blog.utils.invalid.carousel.CarouselValidation;
import com.lwp.blog.utils.invalid.carousel.IsNum;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/03/18:49
 * @Description:
 */
public class CarouselVo implements Serializable {
    @NotBlank(groups = {CarouselValidation.GroupCarouselEdit.class},message = "该轮播图不存在，请刷新页面重试")
    private String id;

    @NotBlank(message = "轮播图名称不能为空")
    private String name;
    @NotBlank(message = "轮播图详情不能为空")
    private String detail;

    private String path;
    private String url;
    private String status;
    private String isDelete;
    private String type;
    private String sid;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String updateUserId;

    @IsNum(message = "排序值不填或者必须为大于0的数字")
    private String sort;
    @NotBlank(message = "轮播图片不能为空")
    private String attachmentId;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
