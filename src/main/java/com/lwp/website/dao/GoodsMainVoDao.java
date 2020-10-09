package com.lwp.website.dao;

import com.lwp.website.entity.Vo.GoodsMainVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/25/15:47
 * @Description:
 */
@Component
public interface GoodsMainVoDao {

    /**
     *
     */
     int insert(GoodsMainVo goodsMainVo);


     List<GoodsMainVo> getList();

}
