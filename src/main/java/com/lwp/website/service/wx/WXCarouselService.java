package com.lwp.website.service.wx;

import com.lwp.website.entity.Vo.CarouselVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/09/11:51
 * @Description:
 */

public interface WXCarouselService {
    List<CarouselVo> getCarouselListByStatus();
}
