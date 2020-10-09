package com.lwp.website.entity.Vo;

/**
 * Created with IntelliJ IDEA.
 * 商品主类
 * @Auther: liweipeng
 * @Date: 2020/09/25/15:36
 * @Description:
 */
public class GoodsMainVo {

    private String id;

    private String name;

    private String bigType;

    private String smallType;

    //类型 0 已上架 1 已下架 1 已删除
    private String status;


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

    public String getBigType() {
        return bigType;
    }

    public void setBigType(String bigType) {
        this.bigType = bigType;
    }

    public String getSmallType() {
        return smallType;
    }

    public void setSmallType(String smallType) {
        this.smallType = smallType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
