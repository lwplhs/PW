package com.lwp.website.service.impl;

import com.lwp.website.dao.DictVoDao;
import com.lwp.website.entity.Vo.DictVo;
import com.lwp.website.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/29/17:55
 * @Description:
 */
@Service
public class DictServiceImpl implements DictService {

    @Resource
    private DictVoDao dictVoDao;

    @Override
    public List<DictVo> getDictList() {

        return null;
    }
}
