package com.lwp.blog.entity.Vo;

import java.io.Serializable;

public class OptionVo implements Serializable {

    private static final long serialVersionUID = -6792475875630006547L;
    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置值
     */
    private String value;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 配置唯一编码
     */
    private String GUID;


    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getGUID() {
        return GUID;
    }
}
