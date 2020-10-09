package com.lwp.website.service.impl;

import com.lwp.website.dao.GoodsMainVoDao;
import com.lwp.website.entity.Vo.GoodsMainVo;
import com.lwp.website.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/25/16:44
 * @Description:
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMainVoDao goodsMainVoDao;

    @Override
    public List<GoodsMainVo> getListCarousel() {
        List<GoodsMainVo> list = goodsMainVoDao.getList();
        return list;
    }
}
