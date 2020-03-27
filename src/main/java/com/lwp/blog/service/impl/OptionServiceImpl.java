package com.lwp.blog.service.impl;

import com.lwp.blog.entity.Vo.OptionVo;
import com.lwp.blog.service.OptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OptionServiceImpl implements OptionService {


    private static Logger LOGGER = LoggerFactory.getLogger(OptionServiceImpl.class);


    @Override
    public void insertOption(OptionVo optionVo) {

    }

    @Override
    public void insertOption(String name, String value) {

    }

    @Override
    public List<OptionVo> getOptions() {
        return null;
    }

    @Override
    public void insertOptions(Map<String, String> options) {

    }

    @Override
    public OptionVo getOptionByName(String name) {
        return null;
    }
}
