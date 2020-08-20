package com.lwp.blog.service;

import com.lwp.blog.entity.Vo.CarouselVo;
import com.lwp.blog.entity.Vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
