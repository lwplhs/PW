package com.lwp.blog.service;

import com.lwp.blog.entity.Vo.OptionVo;

import java.util.List;
import java.util.Map;

public interface OptionService {
    /**
     * 添加一个OptionVo
     * @param optionVo
     */
    void insertOption(OptionVo optionVo);

    /**
     * 添加 name和value
     * @param name
     * @param value
     */
    void insertOption(String name,String value);

    /**
     * 获取OptionVo列表数据
     * @return
     */
    List<OptionVo> getOptions();

    /**
     * 保存一组配置
     * @param options
     */
    void insertOptions(Map<String, String> options);

    /**
     * 通过name来查找OptionVo
     * @param name
     * @return
     */
    OptionVo getOptionByName(String name);
}
