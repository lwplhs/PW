package com.lwp.blog.service.wx.Impl;

import com.lwp.blog.dao.CarouselDao;
import com.lwp.blog.entity.Vo.CarouselVo;
import com.lwp.blog.service.wx.WXCarouselService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/09/11:53
 * @Description:
 */
@Service
public class WXCarouselServiceImpl implements WXCarouselService {

    @Resource
    private CarouselDao carouselDao;

    @Override
    public List<CarouselVo> getCarouselListByStatus() {
        List<CarouselVo> list = carouselDao.getListCarouselByStatus();

        return null;
    }
}
