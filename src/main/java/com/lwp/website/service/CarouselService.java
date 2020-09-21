package com.lwp.website.service;

import com.lwp.website.entity.Vo.CarouselVo;
import com.lwp.website.entity.Vo.UserVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/04/15:26
 * @Description:
 */
public interface CarouselService {


    String saveCarousel(CarouselVo carouselVo, UserVo userVo);

    List<CarouselVo> getListCarousel();

    String getTotalCount();

    List<CarouselVo> getListCarouselByStatus();

    CarouselVo getCarouselById(String id);

    boolean updateCarousel(String ids,String type,UserVo userVo);
}
