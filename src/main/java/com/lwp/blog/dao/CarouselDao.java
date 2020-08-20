package com.lwp.blog.dao;

import com.lwp.blog.entity.Vo.CarouselVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/03/18:51
 * @Description:
 */
@Component
public interface CarouselDao {
    /**
     * 插入轮播图
     * @param carouselVo
     */
    int insertCarousel(CarouselVo carouselVo);

    int updateCarousel(CarouselVo carouselVo);

    /**
     * 获取未删除的轮播图
     * @return
     */
    List<CarouselVo> getListCarousel();

    /**
     * 获取未删除的轮播图的总数
     * @return
     */
    String getTotalCount();

    /**
     * 获取启用的轮播图
     * @return
     */
    List<CarouselVo> getListCarouselByStatus();

    /**
     * 获取最大排序值
     * @return
     */
    int getMaxSort();

    /**
     * 根据id获取 轮播图
     * @param id
     * @return
     */
    CarouselVo getCarouselById(String id);

    /**
     * 更新轮播图 启用/未启用
     * @param map
     * @return
     */
    int updateCarouselWithStatus(Map<String,Object> map);

    /**
     * 更新轮播图 是否删除
     * @param map
     * @return
     */
    int updateCarouseWithDelete(Map<String,Object> map);
}
